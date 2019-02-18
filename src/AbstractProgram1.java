public abstract class AbstractProgram1 {
    public abstract boolean isStableMatching(Matching allocation);

    /**
     * Brute force solution to the Stable Marriage problem. Relies on the function
     * isStableMatching(Matching) to determine whether a candidate Matching is stable.
     *
     * @return A stable Matching.
     */
    public Matching stableMarriageBruteForce(Matching allocation) {
        final long startTime = System.nanoTime();
        int n = allocation.getUserCount();
        int slots = allocation.totalServerSlots();

        Permutation p = new Permutation(n, slots);
        Matching matching;
        while ((matching = p.getNextMatching(allocation)) != null) {
            if (isStableMatching(matching)) {
                final long end = System.nanoTime();
                System.out.println(end-startTime);
                return matching;
            }
        }
        final long endTime = System.nanoTime();
        System.out.println(endTime-startTime);
        return new Matching(allocation);
    }

    public abstract Matching stableMarriageGaleShapley(Matching allocation);
}
