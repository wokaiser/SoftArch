
var controller = (function () {    
    /* try to connect to the websocket */
    var WS = window['MozWebSocket'] ? MozWebSocket : WebSocket;
    var socketAdress = "ws://"+BATTLESHIP_IP+":9000/connect";
    var socket = new WS(socketAdress);

    /* definition of callback lists. A list contains functions, which will be called
       by the callback interface
    */
    var eventListener =
    {
        'init'                 : $.Callbacks(),
        'updatePlayground'     : $.Callbacks(),
        'updateStatus'         : $.Callbacks(),
    };

    /* definition of functions to add callback functions. */
    var addEventListener = function (type, func) {
        eventListener[type].add(func);
    };
    
    /*  function to send an ajax request and handle the return with callbacks. */ 
    var ajaxRequest = function (url, args, successCallback, failCallback) {
        var deferred = $.Deferred();

        /* request to server */
        request = $.ajax({
            url         : url,
            type        : "post",
            contentType : "application/json",
            data        : JSON.stringify(args)
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
    
    var websocketSend = function (data) {
        if (socket.OPEN != socket.readyState) {
            /* fire the failed callback */
            var response = {error : "Websocket is not ready."};
            eventListener["updateStatus"].fire(response);
            return;
        }
        socket.send(JSON.stringify(data));
    };
    
    var websocketReceive = function(event) {
        var data = JSON.parse(event.data);
        if (data.ownPlayground) {
            eventListener["updatePlayground"].fire(data);
        }
        
        if (data.error) {
            eventListener["updateStatus"].fire(data);
        } else if (data.info) {
            eventListener["updateStatus"].fire(data); 
        }
    };
    
    var run = function () {
        /* set the websocket event receiver function */
        socket.onmessage = websocketReceive;
        eventListener["init"].fire();
        /* check weather an error occurr */
        socket.onerror = function (event) {
            eventListener["updateStatus"].fire({error : "Unable to connect websocket at "+socketAdress});
        };
        /* wait until websocket is conntected */
        socket.onopen = function () {
            
        };
    };
    
    var newSinglePlayerGame = function () {
        websocketSend({newSinglePlayerGame : "Creates a new single play game"});
    };
    
    var shoot = function (coord) {
        websocketSend({shootX : coord.x, shootY : coord.y});
    };
    
    /* Public methods and variables */
    return {      
        addEventListener    : addEventListener,
        run                 : run,
        newSinglePlayerGame : newSinglePlayerGame,
        shoot               : shoot,
    };
})();