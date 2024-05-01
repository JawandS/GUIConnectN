package edu.wm.cs.cs301.connectn.controller;
/*
 * Controller for the name input view. 
 */

import edu.wm.cs.cs301.connectn.model.ConnectNModel;

public class NameInputAction {

    /*
     * Constructor for the name input action.
     * Updates the leaderboard with the new name.
     * 
     * @param model the ConnectN model being used
     * @param name the name being input
     */
    public NameInputAction(ConnectNModel model, String name) {
        model.updateLeaderboard(name);
    }
    
}
