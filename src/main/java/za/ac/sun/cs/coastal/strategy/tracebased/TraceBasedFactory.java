package za.ac.sun.cs.coastal.strategy.tracebased;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

import za.ac.sun.cs.coastal.COASTAL;
import za.ac.sun.cs.coastal.messages.Broker;
import za.ac.sun.cs.coastal.messages.Tuple;
import za.ac.sun.cs.coastal.pathtree.PathTree;
import za.ac.sun.cs.coastal.pathtree.PathTreeNode;
import za.ac.sun.cs.coastal.strategy.StrategyFactory;
import za.ac.sun.cs.coastal.symbolic.Execution;
import za.ac.sun.cs.coastal.symbolic.Input;

public abstract class TraceBasedFactory implements StrategyFactory {

	/**
	 * Prefix added to log messages.
	 */
	private static final String LOG_PREFIX = "@T@";

	// ======================================================================
	//
	// TRACE-BASED SEARCH STRATEGY MANAGER
	//
	// ======================================================================

	public abstract static class TraceBasedManager implements StrategyManager {

		protected final COASTAL coastal;

		protected final Broker broker;

		protected final PathTree pathTree;

		/**
		 * Counter of number of refinements.
		 */
		protected final AtomicLong refineCount = new AtomicLong(0);

		/**
		 * Accumulator of all the refinement times.
		 */
		protected final AtomicLong strategyTime = new AtomicLong(0);

		/**
		 * Accumulator of all the strategy waiting times.
		 */
		private final AtomicLong strategyWaitTime = new AtomicLong(0);

		/**
		 * Counter for the strategy waiting times.
		 */
		private final AtomicLong strategyWaitCount = new AtomicLong(0);

		public TraceBasedManager(COASTAL coastal) {
			this.coastal = coastal;
			broker = coastal.getBroker();
			broker.subscribe("coastal-stop", this::report);
			pathTree = coastal.getPathTree();
		}

		public PathTree getPathTree() {
			return pathTree;
		}

		public PathTreeNode insertPath0(Execution execution, boolean infeasible) {
			return pathTree.insertPath(execution, infeasible);
		}

		public boolean insertPath(Execution execution, boolean infeasible) {
			return (pathTree.insertPath(execution, infeasible) == null);
		}

		/**
		 * Increment the number of refinements.
		 */
		public void incrementRefinements() {
			refineCount.incrementAndGet();
		}

		/**
		 * Add a reported dive time to the accumulator that tracks how long the
		 * dives took.
		 * 
		 * @param time
		 *            the time for this dive
		 */
		public void recordTime(long time) {
			strategyTime.addAndGet(time);
		}

		/**
		 * Add a reported strategy wait time. This is used to determine if it
		 * makes sense to create additional threads (or destroy them).
		 * 
		 * @param time
		 *            the wait time for this strategy
		 */
		public void recordWaitTime(long time) {
			strategyWaitTime.addAndGet(time);
			strategyWaitCount.incrementAndGet();
		}

		public void report(Object object) {
			String name = getName();
			double swt = strategyWaitTime.get() / strategyWaitCount.doubleValue();
			broker.publish("report", new Tuple(name + ".tasks", getTaskCount()));
			broker.publish("report", new Tuple(name + ".wait-time", swt));
			broker.publish("report", new Tuple(name + ".total-time", strategyTime.get()));
		}

		protected abstract int getTaskCount();

		private static final String[] PROPERTY_NAMES = new String[] { "#tasks", "#refinements", "waiting time",
				"total time" };

		@Override
		public String[] getPropertyNames() {
			return PROPERTY_NAMES;
		}

		@Override
		public Object[] getPropertyValues() {
			Object[] propertyValues = new Object[4];
			int index = 0;
			double swt = strategyWaitTime.get() / strategyWaitCount.doubleValue();
			long c = refineCount.get();
			long t = strategyTime.get();
			propertyValues[index++] = getTaskCount();
			propertyValues[index++] = String.format("%d (%.1f/sec)", c, c / (0.001 * t));
			propertyValues[index++] = swt;
			propertyValues[index++] = t;
			return propertyValues;
		}

	}

	// ======================================================================
	//
	// TRACE-BASED SEARCH STRATEGY
	//
	// ======================================================================

	public abstract static class TraceBasedStrategy extends Strategy {

		protected final TraceBasedManager manager;

		protected final Broker broker;

		protected final Set<String> visitedModels = new HashSet<>();

		public TraceBasedStrategy(COASTAL coastal, StrategyManager manager) {
			super(coastal, manager);
			this.manager = (TraceBasedManager) manager;
			broker = coastal.getBroker();
		}

		@Override
		public void run() {
			log.trace("{} strategy task starting", LOG_PREFIX);
			try {
				while (true) {
					long t0 = System.currentTimeMillis();
					Execution execution = coastal.getNextTrace();
					long t1 = System.currentTimeMillis();
					manager.recordWaitTime(t1 - t0);
					manager.incrementRefinements();
					log.trace("{} starting refinement", LOG_PREFIX);
					List<Input> inputs = refine(execution);
					int d = -1;
					while (inputs != null) {
						int m = coastal.addSurferInputs(inputs);
						if (m > 0) {
							d += m;
							break;
						}
						inputs = refine1();
					}
					log.trace("{} added {} surfer models", LOG_PREFIX, d);
					coastal.updateWork(d);
				}
			} catch (InterruptedException e) {
				log.trace("{} strategy task canceled", LOG_PREFIX);
				// throw e;
			}
		}

		protected List<Input> refine(Execution execution) {
			long t0 = System.currentTimeMillis();
			List<Input> newModels = refine0(execution);
			manager.recordTime(System.currentTimeMillis() - t0);
			return newModels;
		}

		protected abstract List<Input> refine0(Execution execution);

		protected abstract List<Input> refine1();

	}

}
