let zooms = [];
let pageSizeZoom = 5;
let pageNumberZoom = 1;
let sortBy = "id";
let sortType = "DESC";
let apiZoom = "http://localhost:8888/api/v1/zoom"

let zoomName = "";
let link= "";

function SearchZoomRequest(pageSizeZoom, pageNumberZoom, sortBy, sortType,name, link){
this.pageSize = pageSizeZoom;
this.pageNumber = pageNumberZoom;
this.sortFiled = sortBy;
this.sortType = sortType;
this.name = name;
this.link = link;
}
$(function () {
    console.log(role);
    console.log("Zoom")
    getListZoom();
    checkAdmin();
})

function checkAdmin() {
    if(role != 'ADMIN'){
        $('#zoom-add-button').empty();
    }
}

function buildZoomPage() {
    zooms = [];
    getListZoom();
}

function searchZoom() {
    pageNumberZoom = 1;
    zoomName = document.getElementById("seachname").value;
    getListZoom();
}


// gọi api GetAllZoom
async function getListZoom() {
    let request = new SearchZoomRequest(pageSizeZoom, pageNumberZoom - 1, sortBy, sortType, zoomName, link);
    $.ajax({
        url: apiZoom + "/search",
        type: "POST",
        beforeSend: function (xhr) {
          xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem("token"));
        },
        contentType: "application/json",
        data: JSON.stringify(request),
        error: function (err) {
          console.log(err)
          showAlrtError("Lấy danh sách zoom thất bại");
        },
        success: function (data) {
            fillZoomToTable(data.content);
            buildPaginationZoom(data.number + 1, data.totalPages);
        }
      });
    // fetch('./assets/data/zoom.json')
    //     .then((response) => response.json())
    //     .then((json) => {
    //         fillZoomToTable(json.content);
    //         buildPaginationZoom(json.number + 1, json.totalPages);
    //     }
    //     );
}

function fillZoomToTable(json) {
    if (json) {
        zooms = json;
    }
    // check form trống để k lặp lại khi chuyền data
    $('tbody').empty();
    var index = 1;
    console.log(zooms);


    zooms.forEach(function (item) {
        let actionAdmin = role =='ADMIN' ? '<a class="edit" title="go to  detail" data-toggle="tooltip" onclick="editZoom(' +
        item.id + ')"><i class="ti-pencil m-1 text-warning" style="font-size:24px; cursor: pointer;"></i></a>' +
        '<a class="edit" title="go to  detail" data-toggle="tooltip" onclick="confirmDeleteZoom(' +
        item.id + ')"><i class="ti-trash m-1 text-danger" style="font-size:24px; cursor: pointer;"></i></a>' + '</td>' : '' ;
        $('tbody').append(
            '<tr>' +
            '<td>' + (index++) + '</td>' +
            '<td>' + item.name + '</td>' +
            '<td><a target="_blank" href=' + '"' + item.link + '"> ' + item.link + '<a/></td>' +
            '<td>' + item.meetingId + '</td>' +
            '<td>' + item.passCode + '</td>' +
            '<td>' + item.description + '</td>' +
            '<td>' + actionAdmin +
            '</tr>'
        )

    });

}

function buildPaginationZoom(number, totalPages) {
    // kiểm tra nếu trang hiện tại là trang đầu -> disable đi

    if (number === 1) {
        $("#pagination-zoom").empty().append(
            `<li class="page-item">
            <a class="page-link disabled" aria-label="Previous">
                <span aria-hidden="true">&laquo;</span>
                <span class="sr-only">Previous</span>
            </a>
        </li>`);
    } else {
        $("#pagination-zoom").empty().append(
            `<li class="page-item">
            <a class="page-link" aria-label="Previous" onclick="prePageZoom()">
                <span aria-hidden="true">&laquo;</span>
                <span class="sr-only">Previous</span>
            </a>
        </li>`);
    }

    // Dùng hàm for để build ra số trang. Kiểm tra xem trang hiện tại là bao nhiêu thì background vàng
    for (let index = 1; index <= totalPages; index++) {
        if (number === (index)) {
            $('#pagination-zoom').append(`<li class="page-item "><a class="page-link bg-primary">`+index+`</a></li>`);
        } else {
            $('#pagination-zoom').append(`<li class="page-item"><a class="page-link" onclick="chosePageZoom(` + index + `)">`+index+`</a></li>`);
        }
    }

    // Kiểm tra nếu trang hiện tại là trang cuối -> disable đ
    if (number === totalPages) {
        $("#pagination-zoom").append(
            `<li class="page-item">
            <a class="page-link" aria-label="Next">
                <span aria-hidden="true">&raquo;</span>
                <span class="sr-only">Next</span>
            </a>
        </li>`);
    } else {
        $("#pagination-zoom").append(
            `<li class="page-item">
            <a class="page-link" aria-label="Next" onclick="nextPageZoom()">
                <span aria-hidden="true">&raquo;</span>
                <span class="sr-only">Next</span>
            </a>
        </li>`);
    }
}

function chosePageZoom(page) {
    pageNumberZoom = page;
    getListZoom();
}
function prePageZoom() {
    pageNumberZoom--;
    getListZoom();
}

function nextPageZoom() {
    pageNumberZoom++;
    getListZoom();
}

function addZoom() {
    resetFormEditZoom();
    $('#zoomModal').modal('show')
}

function editZoom(zoomId) {
    let zoom = zooms.find(zoom => zoom.id === zoomId)
    console.log(zoom);
    resetFormEditZoom();
    $('#zoomIdToSave').val(zoom.id);
    $('#zoomName').val(zoom.name);
    $("#linkZoom").val(zoom.link);
    $("#meetingId").val(zoom.meetingId);
    $("#passCode").val(zoom.passCode);
    // $("#classUseZoom").append(zoom.class);
    $("#zoomDescription").val(zoom.description);

    $('#zoomModal').modal('show')
}

function saveZoom() {
    // Lấy các thông số để lưu
    let id = $("#zoomIdToSave").val();
    let name = $("#zoomName").val();
    let link = $("#linkZoom").val();
    let description = $("#zoomDescription").val();
    let meetingId = $("#meetingId").val();
    let passCode = $("#passCode").val();

    let request = {
        "description": description,
        "id": id,
        "link": link,
        "meetingId": meetingId,
        "name": name,
        "passCode": passCode
    };
    
    let api = id ? apiZoom +"/update" : apiZoom + "/create";
    let method = id ? "PUT": "POST";
    let  message = id ? "Update thành công" : "Thêm mới thành công"

    $.ajax({
        url: api,
        type: method,
        beforeSend: function (xhr) {
          xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem("token"));
        },
        contentType: "application/json",
        data: JSON.stringify(request),
        error: function (err) {
          console.log(err)
          showAlrtError(err.responseJSON.message);
        },
        success: function (data) {
            $('#zoomModal').modal('hide')
            showAlrtSuccess(message);
            getListZoom();
        }
      });


    // $('#zoomModal').modal('hide')
    // showAlrtSuccess(text);

}

function resetFormEditZoom() {
    document.getElementById("zoomIdToSave").value = "";
    document.getElementById("zoomName").value = "";
    document.getElementById("linkZoom").value = "";
    document.getElementById("meetingId").value = "";
    document.getElementById("passCode").value = "";
    // document.getElementById("classUseZoom").innerHTML = "";
    document.getElementById("zoomDescription").value = "";
}


function confirmDeleteZoom(zoomId) {
    $('#confirmDeleteZoom').modal('show')
    $('#zoomIdToDelete').val(zoomId)
}

function deleteZoom() {
    let zoomId = document.getElementById("zoomIdToDelete").value;
    console.log(zoomId);

    $.ajax({
        url: apiZoom + "/" + zoomId,
        type: "DELETE",
        beforeSend: function (xhr) {
          xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem("token"));
        },
        contentType: "application/json",
        // data: JSON.stringify(request),
        error: function (err) {
          console.log(err)
          showAlrtError("Delete Zoom Không thành công");
        },
        success: function (data) {
            $('#confirmDeleteZoom').modal('hide')
            showAlrtSuccess("Xoá Zoom thành công!");
            getListZoom();
        }
      });
};
