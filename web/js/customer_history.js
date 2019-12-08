/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(document).ready(function () {
    loadHistoryRequest();
});

function loadHistoryRequest() {
    return $.ajax({
        url: "loadHistory",
        type: "POST",
        success: function (result) {
            loadAllHistory(result.listOrders);
        }
    });
}

$('#formSearch').submit(function (e) {
    e.preventDefault();

    var form = $('#formSearch');
    $.ajax({
        url: "searchHistory",
        type: "POST",
        data: form.serialize(),
        success: function (result) {
            loadAllHistory(result.listOrders);
        }
    });
});

function deleteHistory(orderId) {
    $.ajax({
        url: "deleteHistory",
        type: "POST",
        data: {
            orderId: orderId
        },
        success: function (result) {
            if (result.deleteResult === "success") {
                $('#addToCartTitle').attr('class', 'modal-title text-success');
                $('#addToCartTitle').text('Success');
                $('#addToCartBody').text('Delete Success!');
                // show the modal
                $('#addToCartModal').modal('show');
                loadHistoryRequest();
            } else {
                $('#addToCartTitle').attr('class', 'modal-title text-danger');
                $('#addToCartTitle').text('Failed');
                $('#addToCartBody').text('Delete Failed!');
                // show the modal
                $('#addToCartModal').modal('show');
            }
        }
    });
}

function loadAllHistory(list) {
    $('#tblHistory tr').remove();
    var rowContent = "";
    if (list.length > 0) {
        $.each(list, function (i, order) {
            rowContent = "<tr>";
            rowContent += "    <td>" + (i + 1) + "</td>";
            rowContent += "    <td>" + order.orderId + "</td>";
            rowContent += "    <td>";
            rowContent += "        <p>";
            $.each(order.listHotels, function (x, hotel) {
                rowContent += "            <i class='h5 text-secondary font-weight-bold'>- " + hotel.name + "</i> - <i class='h5 font-weight-bold text-primary'>$" + hotel.subTotalHistory.toFixed(2) + "</i></br>";
                rowContent += "            <small class='text-blue ml-4'>+ " + hotel.type + " Room - x" + hotel.quantityCart + "</small></br>";
                rowContent += "            <small class='text-primary ml-4'>+ " + hotel.checkIn + " to " + hotel.checkOut + "</small></br>";
            });
            rowContent += "        </p>";
            rowContent += "    </td>";
            rowContent += "    <td class='text-green font-weight-bold'>-$" + order.couponValue.toFixed(2) + "</td>";
            rowContent += "    <td class='h4 text-primary font-weight-bold'>$" + order.total.toFixed(2) + "</td>";
            rowContent += "    <td><button class='btn btn-link pt-0' onclick=\"deleteHistory('" + order.orderId + "')\"><i class='h4 fa fa-times'></i></button></td>";
            rowContent += "</tr>";

            $('#tblHistory').append(rowContent);
        });
    }else{
        rowContent = "<tr class='text-danger text-center h3 font-weight-bold'><td colspan='6'>There's no history!</td></tr>";
        $('#tblHistory').append(rowContent);
    }
}