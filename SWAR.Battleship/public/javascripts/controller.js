
var controller = (function () {    

    /* definition of callback lists. A list contains functions, which will be called
       by the callback interface
    */
    var eventListener =
    {
        'error'                : $.Callbacks(),
        'init'                 : $.Callbacks(),
        'updatePlayground'     : $.Callbacks(),
    };

    /* definition of functions to add callback functions. */
    var addEventListener = function (type, func) {
        eventListener[type].add(func);
    };
    
    var serverRequest = function (url, args, successCallback, failCallback) {
        var deferred = $.Deferred();

        /* request to server */
        request = $.ajax({
            url   : url,
            type  : "post",
            data  : args
        });

        /* callback handler that will be called on success */
        request.done(function (response, textStatus, jqXHR){
            if (response.error) {
                eventListener[failCallback].fire(response);
                deferred.reject();
            } else {
                eventListener[successCallback].fire(response);
                deferred.resolve();
            }
        });

        /* callback handler that will be called on failure */
        request.fail(function (jqXHR, textStatus, errorThrown){
            /* fire the failed callback */
            var response = {status : {error : "Server request failed"}};
            eventListener[failCallback].fire(response);
            deferred.reject();
        });

        return deferred.promise();
    };
    
    var data = {
        status          : null,
        runningGame     : true,
        enemyPlayground : [[{x : 0, y : 0, state : 'x'}, {x : 1, y : 0, state : '0'}],
                           [{x : 0, y : 1, state : 'x'}, {x : 1, y : 1, state : '0'}]],
        ownPlayground   : [[{x : 0, y : 0, state : 'x'}, {x : 1, y : 0, state : '0'}],
                           [{x : 0, y : 1, state : 'x'}, {x : 1, y : 1, state : '0'}]],
    };
    
    var run = function () {
        console.log("run");
        eventListener["init"].fire();
        eventListener["updatePlayground"].fire(data);
        serverRequest("/loadGame", {name : "temp"}, "updatePlayground", "init");
    };
    
    /* Public methods and variables */
    return {      
        addEventListener : addEventListener,
        run              : run,
    };
})();
