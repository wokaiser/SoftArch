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
    });


/* start application controller */
controller.run();
