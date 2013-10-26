
var content = (function () {    

    var init = function () {
        console.log("init");
    };
    
    var updatePlayground = function (playground) {
        $('#playground').html(Mustache.to_html($('#playgroundTemplate').html(), playground));
        eventHandle.clickablePlayground();
    };
    
    var updateStatus = function (status) {
        $('#status').html(Mustache.to_html($('#statusTemplate').html(), status));
    };
    
    /* Public methods and variables */
    return {      
        init             : init,
        updatePlayground : updatePlayground,
        updateStatus     : updateStatus,
    };
})();
