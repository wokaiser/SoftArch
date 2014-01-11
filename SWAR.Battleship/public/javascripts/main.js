String.prototype.replaceAll = function(search, replace)
{
    //if replace is null, return original string otherwise it will
    //replace search string with 'undefined'.
    if(!replace) 
        return this;

    return this.replace(new RegExp('[' + search + ']', 'g'), replace);
};

/* add callbacks to application controller */
controller.addEventListener('init', content.init);
controller.addEventListener('updatePlayground', content.updatePlayground);
controller.addEventListener('updateStatus', content.updateStatus);
controller.addEventListener('highscore', 
    function(score) {
        $('#myModalTitle').html("HighScore");
        $('#myModalBody').html(score.replaceAll("\n", "<br/>"));
        $('#myModal').modal('toggle');
    }
);
    
    
controller.addEventListener('getSavegames', 
    function(games) {
        $('#loadModalBody').html(games);
        $('#loadModal').modal('toggle');
    }
);

controller.addEventListener('saveGameError', 
    function(error) {
        alert(error);
    }
);

controller.addEventListener('saveGameSuccess', 
    function(success) {
        $('#saveModal').modal('toggle');
        alert(success);
    }
);


/* start application controller */
controller.run();
