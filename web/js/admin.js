/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function () {
    // Load all hotel with available rooms
    loadAllHotels();

    //Set current for checkin date
    var now = new Date();
    var day = ("0" + now.getDate()).slice(-2);
    var month = ("0" + (now.getMonth() + 1)).slice(-2);
    var today = now.getFullYear() + "-" + (month) + "-" + (day);
    $('#dateFrom').val(today);

    // Set current for checkout date
    var now = new Date();
    now.setDate(now.getDate() + 1);
    var day = ("0" + now.getDate()).slice(-2);
    var month = ("0" + (now.getMonth() + 1)).slice(-2);
    var tommorrow = now.getFullYear() + "-" + (month) + "-" + (day);
    $('#dateTo').val(tommorrow);
});

$('#formSearch').on('submit', function (e) {
    e.preventDefault();

    var now = new Date();
    now.setHours(0, 0, 0, 0);
    var dateFrom = new Date($('#dateFrom').val());
    var dateTo = new Date($('#dateTo').val());

    if (dateFrom < now) {
        $('#dateMsg').text("Check in date must greater than current date!");
        $('#dateModal').modal('show');
    } else if (dateFrom >= dateTo) {
        $('#dateMsg').text("Check out date must greater than check in!");
        $('#dateModal').modal('show');
    } else {
        searchHotels();
        // Get height of the document
        var h = $(window).height();
        $('html, body').animate({scrollTop: h}, 500, "swing");
    }
});

function loadAllHotels() {
    return $.ajax({
        url: "loadAllHotel",
        type: "POST",
        success: function (data) {
            showHotels(data.listHotels);
        }
    });
}

function searchHotels() {
    return $.ajax({
        url: "searchHotel",
        type: "POST",
        data: $('#formSearch').serialize(),
        success: function (data) {
            showHotels(data.listHotels);
        }
    });
}

function showHotels(listHotels) {
    $('#listHotelContainer div').remove();
    var rowContent = "";
    if (listHotels.length < 1) {
        rowContent = "<div class='row mt-5'><div class='col h2 text-center font-weight-bold text-danger'>There no hotel room available now!</di></di>";
        $('#listHotelContainer').append(rowContent);
    } else {
        $.each(listHotels, function (i, hotel) {
            rowContent = "<div class='row mt-4'>";
            rowContent += "<div class='card col card-material'>";
            rowContent += "<div class='card-body'>";
            rowContent += "<div class='row'>";
            rowContent += "<div class='col-3'>";
            rowContent += "<img src='" + hotel.photo + "' alt='Hotel Photo' class='img-hotel rounded'>";
            rowContent += "</div>";
            rowContent += "<div class='col-6 mt-2'>";
            rowContent += "<h3 class='text-secondary font-weight-bold card-title'>" + hotel.name + "</h3>";
            rowContent += "<h5 class='card-text'>" + hotel.address + "</h5>";
            rowContent += "<p class='card-text text-blue font-weight-bold mb-1' >" + hotel.type + " Rom</p>";
            rowContent += "<p class='card-text font-italic text-red font-weight-bold'>Maximun People: " + hotel.maxPeople + "</p>";
            rowContent += "</div>";
            rowContent += "<div class='col ml-5 mt-3'>";
            rowContent += "<p class='h4 font-weight-bold card-text text-primary'>$" + hotel.subTotal.toFixed(2) + "</p>";
            rowContent += "<p class='text-orange card-text font-weight-bold my-0'>Price for " + hotel.rentDay + " day(s)</p>";
            rowContent += "<p class='card-text text-green font-weight-bold font-italic my-0'>" + hotel.availableRoom + " room(s) available</p>";
            rowContent += "<s:property value=''>";
            rowContent += "</div>";
            rowContent += "</div>";
            rowContent += "</div>";
            rowContent += "</div>";
            rowContent += "</div>";
            $('#listHotelContainer').append(rowContent);
        });
    }
}


