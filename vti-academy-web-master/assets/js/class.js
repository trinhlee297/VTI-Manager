let classList = [];
let pageSizeClass = 5;
let pageNumberClass = 1;
let sortByClass = "id";
let sortTypeClass = "DESC";
let apiClass = "http://localhost:8888/api/v1/classEntity"

let className  = "";

$(function () {
    buildClassPage();
    buildClassRoomToForm();
    buildMentorToForm();
    buildZoomToForm();
})

function SearchClassRequest(pageSizeClass, pageNumberClass, sortBy, sortType,className){
    this.pageSize = pageSizeClass;
    this.pageNumber = pageNumberClass;
    this.sortFiled = sortBy;
    this.sortType = sortType;
    this.className = className;
    }

function buildClassPage() {
    classList = [];
    getListClass();
}

function searchClass() {
    pageNumberClass = 1;
    className  = document.getElementById("seachname").value;
    getListClass();
}


//  ----------------------------------------------------------------------------=
// gọi api GetAllClass
async function getListClass() {
    let request = new SearchClassRequest(pageSizeClass, pageNumberClass - 1, sortByClass, sortTypeClass, className);
    $.ajax({
        url: apiClass + "/search",
        type: "POST",
        beforeSend: function (xhr) {
          xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem("token"));
        },
        contentType: "application/json",
        data: JSON.stringify(request),
        error: function (err) {
          console.log(err)
          showAlrtError("Lấy danh sách class thất bại");
        },
        success: function (data) {
            fillClasstoTable(data.content);
            buildPaginationClass(data.number + 1, data.totalPages);
        }
      });
}
function fillClasstoTable(json) {
    if (json) {
        classList = json;
    }
    // check form trống để k lặp lại khi chuyền data
    $('#table-class').empty();
    var index = 1;
    console.log(classList);
    classList.forEach(function (item) {
        $('#table-class').append(
            '<tr>' +
            '<td>' + (index++) + '</td>' +
            '<td> ' + item.className + '</td>' +
            '<td>' + item.startDate + '</td>' +
            '<td>' + item.endDate + '</td>' +
            '<td>' + item.status + '</td>' +
            '<td>' + item.teachingFrom + '</td>' +
            '<td>' + item.description + '</td>' +
            '<td>' + item.mentor + '</td>' +
            '<td>' + item.classRoom.name+ '</td>' +
            '<td>' + item.zoom.name + '</td>' +

            '<td>' +
            '<a class="edit" title="go to  detail" data-toggle="tooltip" onclick="editClass(' +
            item.id + ')"><i class="ti-pencil m-1 text-warning" style="font-size:24px; cursor: pointer;"></i></a>' +
            '<a class="edit" title="go to  detail" data-toggle="tooltip" onclick="confirmDeleteClass(' +
            item.id + ')"><i class="ti-trash m-1 text-danger" style="font-size:24px; cursor: pointer;"></i></a>' +
            '<a class="edit" title="go to  detail" data-toggle="tooltip" onclick="viewStudent(' +
            item.id + ')"><i class="ti-search m-1 text-primary" style="font-size:24px; cursor: pointer;"></i></a>' +
            '</td>' +
            '</tr>'
        )
    });
}


function buildPaginationClass(number, totalPages) {
    // kiểm tra nếu trang hiện tại là trang đầu -> disable đi

    if (number === 1) {
        $("#pagination-class").empty().append(
            `<li class="page-item">
            <a class="page-link disabled" aria-label="Previous">
                <span aria-hidden="true">&laquo;</span>
                <span class="sr-only">Previous</span>
            </a>
        </li>`);
    } else {
        $("#pagination-class").empty().append(
            `<li class="page-item">
            <a class="page-link" href="#" aria-label="Previous" onclick="prePageClass()">
                <span aria-hidden="true">&laquo;</span>
                <span class="sr-only">Previous</span>
            </a>
        </li>`);
    }

    // Dùng hàm for để build ra số trang. Kiểm tra xem trang hiện tại là bao nhiêu thì background vàng
    for (let index = 1; index <= totalPages; index++) {
        if (number === (index)) {
            $('#pagination-class').append(`<li class="page-item "><a class="page-link bg-primary" href="#" onclick="chosePageClass(` + index + `)">` + index + `</a></li>`);
        } else {
            $('#pagination-class').append(`<li class="page-item"><a class="page-link" href="#" onclick="chosePageClass(` + index + `)">` + index + `</a></li>`);
        }
    }

    // Kiểm tra nếu trang hiện tại là trang cuối -> disable đ
    if (number === totalPages) {
        $("#pagination-class").append(
            `<li class="page-item">
            <a class="page-link" aria-label="Next">
                <span aria-hidden="true">&raquo;</span>
                <span class="sr-only">Next</span>
            </a>
        </li>`);
    } else {
        $("#pagination-class").append(
            `<li class="page-item">
            <a class="page-link" href="#" aria-label="Next" onclick="nextPageClass()">
                <span aria-hidden="true">&raquo;</span>
                <span class="sr-only">Next</span>
            </a>
        </li>`);
    }
}

function chosePageClass(page) {
    pageNumberClass = page;
    getListClass();
}
function prePageClass() {
    pageNumberClass--;
    getListClass();
}

function nextPageClass() {
    pageNumberClass++;
    getListClass();
}

function addClass(){
    resetFromEditClass();
    $('#classModal').modal('show')
}

function editClass(classId){
    let class_ = classList.find(class_ => class_.id === classId)
    console.log(class_);
    resetFromEditClass();
    $('#cl-id').val(class_.id);
    $('#cl-name').val(class_.className);
    $('#cl-startDate').val(new Date(class_.startDate).toISOString().substring(0, 10));
    $("#cl-endDate").val(new Date(class_.endDate).toISOString().substring(0, 10));
    $("#cl-classStatus").val(class_.status);
    $("#cl-teachingForm").val(class_.teachingFrom);
    $("#cl-mentor").val(class_.mentor.id);
    $("#cl-classRoomId").val(class_.classRoom.id);
    $("#cl-zoomId").val(class_.zoom.id);
    $("#cl-information").val(class_.description);

    $('#classModal').modal('show')
}

function saveClass() {
    // Lấy các thông số để lưu
    let accountId = $('#cl-id').val();
    let name = $('#cl-name').val();
    let startDate = $('#cl-startDate').val();
    let endDate = $("#cl-endDate").val();
    let status = $("#cl-classStatus").val();
    let teachingForm = $("#cl-teachingForm").val();
    let mentor = $("#cl-mentor").val();
    let classRoomId = $("#cl-classRoomId").val();
    let zoomId = $("#cl-zoomId").val();
    let information = $("#cl-information").val();

    
// ---------------------------------- CALL API UPDATE OR CREATE ----------------------------------

    let request = {
        "className": name,
        "id": accountId,
        "startDate": startDate,
        "endDate": endDate,
        "status": status,
        "teachingFrom": teachingForm,
        "accountId": mentor,
        "classRoomId": classRoomId,
        "zoomId": zoomId,
        "description": information,
    };
    
    let api = accountId ? apiClass +"/update" : apiClass + "/create";
    let method = accountId ? "PUT": "POST";
    let  message = accountId ? "Update thành công" : "Thêm mới thành công"

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
          showAlrtError("Update thất bại");
        },
        success: function (data) {
            $('#classModal').modal('hide')
            showAlrtSuccess(message);
            getListClass();
        }
      });



    // let text = accountId ? "Update Class thành công" : "Thêm mới Class thành công"

    // $('#classModal').modal('hide')
    // showAlrtSuccess(text);
}

function resetFromEditClass(){
    // $('#cl-id').val("");
    // $('#cl-name').val("");
    // $('#cl-startDate').val("");
    // $("#cl-endDate").val("");
    // $("#cl-classStatus").val("");
    // $("#cl-teachingForm").val("");
    // $("#cl-mentor").val("");
    // $("#cl-classRoomId").val("");
    // $("#cl-zoomId").val("");
    // $("#cl-information").val("");

    document.getElementById("cl-id").value = "";
    document.getElementById("cl-name").value = "";
    document.getElementById("cl-startDate").value = "";
    document.getElementById("cl-endDate").value = "";
    document.getElementById("cl-classStatus").value = "";
    document.getElementById("cl-teachingForm").value = "";
    document.getElementById("cl-mentor").value = "";
    document.getElementById("cl-classRoomId").value = "";
    document.getElementById("cl-zoomId").value = "";
    document.getElementById("cl-information").value = "";
}

function confirmDeleteClass(classId) {
    $('#confirmDeleteClass').modal('show')
    $('#classIdToDelete').val(classId)
}

function deleteClass() {
    let classId = document.getElementById("classIdToDelete").value;
    console.log(classId);

    $.ajax({
        url: apiClass + "/" + classId,
        type: "DELETE",
        beforeSend: function (xhr) {
          xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem("token"));
        },
        contentType: "application/json",
        // data: JSON.stringify(request),
        error: function (err) {
          console.log(err)
          showAlrtError("Delete Class Không thành công");
        },
        success: function (data) {
            $('#confirmDeleteClass').modal('hide')
            showAlrtSuccess("Xoá Class thành công!");
            getListClass();
        }
      });

    // $('#confirmDeleteClass').modal('hide')
// ---------------------------------- CALL API DELETE ----------------------------------
    // showAlrtSuccess("Xoá account thành công!");
}

function viewStudent(classId){
    console.log(classId)
    // Call API lấy danh sách Account theo classId
    // fake
    fetch('./assets/data/account.json')
        .then((response) => response.json())
        .then((json) => {
            fillAccountToTable(json.content);
        }
        );
    $('#viewStudent').modal('show')
}

function fillAccountToTable(accounts){
    var index = 1;
    $('#table-account').empty()
    accounts.forEach(function (item) {
        $('#table-account').append(
            '<tr>' +
            '<td>' + (index++) + '</td>' +
            '<td>' + item.username + '</td>' +
            '<td>' + item.dateOfBirth + '</td>' +
            '<td>' + item.address + '</td>' +
            '<td>' + item.fullName + '</td>' +
            '<td>' + item.role + '</td>' +
            '<td>' + item.phoneNumber + '</td>' +
            '<td>' + item.email + '</td>' +
            '<td><a target="_blank" href=' + '"' + item.facebook + '"> ' + item.facebook + '<a/></td>' +
            '</tr>'
        )
    });
}

// ------------------------------ Build form add class ----------------------------------
function buildMentorToForm(){
    // -------------------- CALL API Get All Account is Mentor ----------------
    // Fake:
console.log("buildMentor");
    $.ajax({
        url: "http://localhost:8888/api/v1/account/getAll-mentor",
        type: "GET",
        beforeSend: function (xhr) {
          xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem("token"));
        },
        contentType: "application/json",
        error: function (err) {
          console.log(err)
          showAlrtError("Lấy danh sách Class Mentor thất bại")
        },
        success: function (data) {
            fillMentorToForm(data);
        }
      });

    // fetch('./assets/data/account.json')
    // .then((response) => response.json())
    // .then((json) => {
    //     let data = json.content.filter(x => x.role === 'MENTOR');
    //     fillMentorToForm(data);
    // }
    // );
}

function fillMentorToForm(data){
    if(data){
        $('#cl-mentor').empty();
        data.forEach(function (item) {
            $('#cl-mentor').append(
                `<option value="`+item.id +`">`+item.fullName+`</option>`
            )
        });
    }
}

function buildClassRoomToForm(){
    // -------------------- CALL API Get All Class Room ----------------
    // Fake:

    $.ajax({
        url: "http://localhost:8888/api/v1/classRoom/getAll",
        type: "GET",
        beforeSend: function (xhr) {
          xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem("token"));
        },
        contentType: "application/json",
        error: function (err) {
          console.log(err)
          showAlrtError("Lấy danh sách Class Room thất bại")
        },
        success: function (data) {
            fillClassRoomToForm(data);
        }
      });
}

function fillClassRoomToForm(data){
    if(data){
        $('#cl-classRoomId').empty();
        data.forEach(function (item) {
            $('#cl-classRoomId').append(
                `<option value="`+item.id +`">`+item.name+`</option>`
            )
        });
    }
}

function buildZoomToForm(){
    // -------------------- CALL API Get All Zoom ----------------
    // Fake:

    $.ajax({
        url: "http://localhost:8888/api/v1/zoom/getAll",
        type: "GET",
        beforeSend: function (xhr) {
          xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem("token"));
        },
        contentType: "application/json",
        error: function (err) {
          console.log(err)
          showAlrtError("Lấy danh sách Zoom thất bại")
        },
        success: function (data) {
            fillZoomToForm(data);
        }
      });
}

function fillZoomToForm(data){
    if(data){
        $('#cl-zoomId').empty();
        data.forEach(function (item) {
            $('#cl-zoomId').append(
                `<option value="`+item.id +`">`+item.name+`</option>`
            )
        });
    }
}