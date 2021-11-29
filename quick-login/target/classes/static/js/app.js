var stompLogClient = null;
var stompTalkClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    } else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    var socket = new SockJS('logs');
    stompLogClient = Stomp.over(socket);
    stompLogClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected ===> ' + frame);
        stompLogClient.subscribe('/topic/logs', function (greeting) {
            console.log(greeting)
            showGreeting(greeting.body);
        });
    });
}

function disconnect() {
    if (stompLogClient !== null) {
        stompLogClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function login() {
    var name = $('#name').val();
    var soc = new SockJS('talk');
    stompTalkClient = Stomp.over(soc);
    stompTalkClient.connect({}, function () {
        $("#name").prop("disabled", true);
        $("#login").prop("disabled", true);
        stompTalkClient.subscribe('/user/talk/' + name, function (msg) {
            console.log("msg===>", msg)
            showTalk(msg.body);
        });
    });
}

function send() {
    var id = $('#id').val();
    if ($.trim(id)) {
        $('#id').prop("disabled", true);
    }
    var data = {
        from: $('#name').val(),
        to: id,
        message: $("#msg").val()
    };
    $("#msg").val('');
    stompTalkClient.send('/app/send/user', {}, JSON.stringify(data));
}

function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

function showTalk(message) {
    $("#messages").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $("#connect").click(function () {
        connect();
    });
    $("#disconnect").click(function () {
        disconnect();
    });
    $("#login").click(function () {
        login();
    });
    $("#send").click(function () {
        send();
    });
});
