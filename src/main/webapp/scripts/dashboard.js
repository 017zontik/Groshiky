/* globals Chart:false, feather:false */

(function () {
    'use strict'

    feather.replace();

    $("#saveAccount").click(function () {
        if ($("#newAccount")[0].reportValidity()) {
            $.ajax("addAccount", {
                type: "POST",
                data: {name: $("#name").val()},
                statusCode: {
                    200: function (response) {
                        $("#accountModal").modal("hide");
                        let $accountClone = $("#accountTemplate").clone();
                        $("a div div strong", $accountClone).text(response.name);
                        $(".text-success strong", $accountClone).text("0,00 BYN");
                        $(".text-danger", $accountClone).hide();
                        $("#accountsList").append($accountClone);
                        $accountClone.show();
                    },
                    400: function (response) {
                        $("#accountErrorMessage").text(response.responseText).show();
                    },
                },
            });
        }

    });
    $("#accountModal").on("show.bs.modal", function () {
        $("#accountErrorMessage").hide();
        $("#name").val("");

    })


}())


