/*
 * Name: <your name>
 * EID: <your EID>
 */

import java.util.ArrayList;
import java.lang.System.*;


/**
 * Your solution goes in this class.
 * 
 * Please do not modify the other files we have provided for you, as we will use
 * our own versions of those files when grading your project. You are
 * responsible for ensuring that your solution works with the original version
 * of all the other files we have provided for you.
 * 
 * That said, please feel free to add additional files and classes to your
 * solution, as you see fit. We will use ALL of your additional files when
 * grading your solution.
 */
public class Program1 extends AbstractProgram1 {
    /**
     * Determines whether a candidate Matching represents a solution to the
     * Stable Marriage problem. Study the description of a Matching in the
     * project documentation to help you with this.
     */
    public boolean isStableMatching(Matching allocation) {
        int numOfMatches = 0;
        ArrayList<Integer> match = allocation.getUserMatching();
        for (int i = 0; i < allocation.getUserCount(); i++) {
            if (match.get(i) != -1) {
                numOfMatches++;
            }
        }
        if (numOfMatches != allocation.totalServerSlots()) {
            return false;
        }


        int server = 0;
        for (int user = 0; user < match.size() - 1; user++) {
            server = match.get(user);
            if (server != -1) {
                int ServerPref = allocation.getServerPreference().get(server).indexOf(user);
                int Userpref = allocation.getUserPreference().get(user).indexOf(server);
                for (int nextUser = user + 1; nextUser < match.size(); nextUser++) {
                    int otherServPref = allocation.getServerPreference().get(server).indexOf(nextUser);
                    int uMatch = match.get(nextUser);
                    if (uMatch != server) {
                        int upref1 = 0;
                        int upref2 = allocation.getUserPreference().get(nextUser).indexOf(server);
                        if (uMatch == -1) {
                            upref2 = -1;

                        } else {
                            upref1 = allocation.getUserPreference().get(nextUser).indexOf(uMatch);
                            int lastServPref = allocation.getServerPreference().get(uMatch).indexOf(nextUser);
                            int sPref = allocation.getServerPreference().get(uMatch).indexOf(user);
                            int UserPref1 = allocation.getUserPreference().get(user).indexOf(uMatch);
                            if(lastServPref > sPref && Userpref > UserPref1 ){
                                return false;
                            }

                        }
                        if (upref1 > upref2 && ServerPref > otherServPref) {
                            return false;
                        }
                    }
                }
            } else {
                for (int nextUser = user + 1; nextUser < match.size(); nextUser++) {
                    int uMatch = match.get(nextUser);
                    if (uMatch != -1) {
                        int ServPref1 = allocation.getServerPreference().get(uMatch).indexOf(user);
                        int ServPref2 = allocation.getServerPreference().get(uMatch).indexOf(nextUser);
                        if (ServPref1 < ServPref2) {
                            return false;
                        }
                    }
                }
            }
        }
      /*  int finalServer = match.get(match.size() - 1);
        if (finalServer != -1) {
            int currentServerpref = allocation.getServerPreference().get(finalServer).indexOf(match.size() - 1);
            for (int i = match.size() - 1; i >= 0; i--) {
                int predicted_pref = allocation.getServerPreference().get(finalServer).indexOf(i);
                int currentUserPref = 0;
                if (match.get(i) != -1) {
                    currentUserPref = allocation.getUserPreference().get(i).indexOf(match.get(i));
                } else {
                    currentUserPref = -1;
                }
                int predictedUserPref = allocation.getUserPreference().get(i).indexOf(finalServer);
                if (currentUserPref > predictedUserPref && currentServerpref > predicted_pref) {
                    return false;
                }

            }
        } */
        return true;
    }

    /**
     * Determines a solution to the Stable Marriage problem from the given input
     * set. Study the project description to understand the variables which
     * represent the input to your solution.
     *
     * @return A stable Matching.
     */
    public Matching stableMarriageGaleShapley(Matching allocation) {
        final long startTime = System.nanoTime();
        ArrayList<Integer> Matches = new ArrayList<Integer>();
        ArrayList<Integer> slotsPER = new ArrayList<Integer>();
        slotsPER.addAll(allocation.getServerSlots());

        for (int i = 0; i < allocation.getUserCount(); i++) {
            Matches.add(-1);
        }

        while (slotsStill(slotsPER)) {
            int availableServer = firstAvailable(slotsPER);
            for(int i=0; i< allocation.getServerPreference().get(availableServer).size() && slotsPER.get(availableServer)>0; i++){
                int proposedUser = allocation.getServerPreference().get(availableServer).get(i);

                if(Matches.get(proposedUser)==-1){// if user is available then we can set him up with the server
                    slotsPER.set(availableServer, slotsPER.get(availableServer)-1);
                    Matches.set(proposedUser, availableServer);
                }

                else{//user is unavaliable and must be stolen or not stolen
                     int proposedHumanPref = allocation.getUserPreference().get(proposedUser).indexOf(availableServer);
                     int currentMatch =Matches.get(proposedUser);
                     int currentHappiness = allocation.getUserPreference().get(proposedUser).indexOf(currentMatch);

                     if(proposedHumanPref < currentHappiness){
                         Matches.set(proposedUser, availableServer);
                         slotsPER.set(availableServer, slotsPER.get(availableServer)-1);
                         slotsPER.set(currentMatch , slotsPER.get(currentMatch)+1);
                     }
                }
            }
        }
        allocation.setUserMatching(Matches);
        final long endTime = System.nanoTime();
        System.out.println(endTime-startTime);
        return allocation;
    }






    private boolean slotsStill(ArrayList<Integer> slots) {
        for (int i = 0; i < slots.size(); i++) {
            if (slots.get(i) != 0) {
                return true;
            }
        }
        return false;
    }

    private int firstAvailable(ArrayList<Integer> servers){
        int index=0;
        for(index =0; index< servers.size(); index++){
            if(servers.get(index)>0){
                return index;
            }
        }
        return index;
    }

    /*private boolean reverseMethod(Matching allocation, int User, int otherUser, int Server, int otherServer){

           }*/



}

