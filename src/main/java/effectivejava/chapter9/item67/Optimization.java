package effectivejava.chapter9.item67;

/**
 * 
 * Item 67: Optimize judiciously.
 * 
 * There are three aphorisms concerning optimization that everyone should know:
 * 
 * More computing sins are committed in the name of efficiency (without
 * necessarily achieving it) than for any other single reason—including blind
 * stupidity. —William A. Wulf [Wulf72]
 * 
 * We should forget about small efficiencies, say about 97% of the time:
 * premature optimization is the root of all evil. —Donald E. Knuth [Knuth74]
 * 
 * We follow two rules in the matter of optimization: Rule 1. Don’t do it. Rule
 * 2 (for experts only). Don’t do it yet—that is, not until you have a perfectly
 * clear and unoptimized solution. —M. A. Jackson [Jackson75]
 * 
 * All of these aphorisms predate the Java programming language by two decades.
 * They tell a deep truth about optimization: it is easy to do more harm than
 * good, especially if you optimize prematurely. In the process, you may produce
 * software that is neither fast nor correct and cannot easily be fixed.
 * 
 * Don’t sacrifice sound architectural principles for performance. Strive to
 * write good programs rather than fast ones.
 * 
 * Strive to avoid design decisions that limit performance.
 * 
 * Consider the performance consequences of your API design decisions.
 * 
 * Luckily, it is generally the case that good API design is consistent with
 * good performance. It is a very bad idea to warp an API to achieve good
 * performance.
 * 
 * Measure performance before and after each attempted optimization.Profiling
 * tools can help you decide where to focus your optimization efforts.Another
 * tool that deserves special mention is jmh, which is not a profiler but a
 * microbenchmarking framework that provides unparalleled visibility into the
 * detailed performance of Java code [JMH].
 * 
 * To summarize, do not strive to write fast programs—strive to write good ones;
 * speed will follow. But do think about performance while you’re designing
 * systems, especially while you’re designing APIs, wire-level protocols, and
 * persistent data formats. When you’ve finished building the system, measure
 * its performance. If it’s fast enough, you’re done. If not, locate the source
 * of the problem with the aid of a profiler and go to work optimizing the
 * relevant parts of the system. The first step is to examine your choice of
 * algorithms: no amount of low-level optimization can make up for a poor choice
 * of algorithm. Repeat this process as necessary, measuring the performance
 * after every change, until you’re satisfied.
 *
 */

public class Optimization {

}
