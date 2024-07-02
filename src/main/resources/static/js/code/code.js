/**
 * UI적용 / 김경모 / 24.01.11
 */

$(function() {

    memuSelecter();
    function memuSelecter() {
        var currentPath = window.location.pathname;
        $('.nav-menu li').removeClass('active open');
        var $currentMenuItem = $('.nav-menu a[href="' + currentPath + '"]').closest('li');
        $currentMenuItem.addClass('active');
        $currentMenuItem.parents('li').addClass('active open');
        //console.log("currentPath:",currentPath);
    }

    if(groupNum != '') {
        $('.code-group-data').removeClass('table-primary');
        $('.code-group-data').each(function(){
            let num = $(this).find('.group-num').text();
            if(groupNum == num){
                $(this).addClass('table-primary');
                codeGroupDetail(num, codeNum);
            }
        });
    } else {
        $('.code-group-data:first').addClass('table-primary');
        groupNum = $('.code-group-data:first').find('.group-num').text();
        codeGroupDetail(groupNum, codeNum);
    }

    $('.table').on('click', '.code-group-data', function(){
        if(!$(this).hasClass('table-primary')){
            $('.code-group-data').removeClass('table-primary');
            $(this).addClass('table-primary');
            let num = $(this).find('.group-num').text();
            var codeNum = '';
            codeGroupDetail(num, codeNum);
        }
    });

    $('#code-group-btn').on('click', function(){
        $('.code-group-data').removeClass('table-primary');
        $('#code-group-detail-field').hide();
        $('#code-group-create-field').show();
    });

    function codeGroupDetail(groupNum, codeNum){
        $('#code-group-btn').removeClass('onClicked');
        $.ajax({
            url: '/system/code/selectCodeGroup.do',
            type: 'get',
            data: { groupNum: groupNum },
            dataType: 'json',
            success: function(response){
                if(response != null){
                    $('.table.detail-code-group').find('#detail-group-name').val(response.codeGroupName);
                    $('.table.detail-code-group').find('#detail-group-num').val(response.codeGroup);
                    $('.table.detail-code-group').find('#detail-group-note').val(response.note);
                    $('#code-group-detail-field').show();
                    $('#code-group-create-field').hide();
                } else {
                    $('.table.detail-code-group').find('#detail-group-name').val('');
                    $('.table.detail-code-group').find('#detail-group-num').val('');
                    $('.table.detail-code-group').find('#detail-group-note').val('');
                }
            }
        });

        $.ajax({
            url: '/system/code/selectCodeList.do',
            type: 'get',
            data: { groupNum: groupNum, codeNum: codeNum },
            success: function(response) {
                $('.space').html(response);
                if (codeNum != '') {
                    $('.code-data').removeClass('table-primary');
                    $('.code-data').each(function() {
                        let num = $(this).find('.code-num').text();
                        if (codeNum == num) {
                            $(this).addClass('table-primary');
                            selectCodeDetail(num);
                        }
                    });
                } else {
                    $('.code-data:first').addClass('table-primary');
                    let num = $('.code-data:first').find('.code-num').text();
                    selectCodeDetail(num);
                }

                $('.table').on('click', '.code-data', function() {
                    if (!$(this).hasClass('table-primary')) {
                        $('.code-data').removeClass('table-primary');
                        $(this).addClass('table-primary');
                        let num = $(this).find('.code-num').text();
                        selectCodeDetail(num);
                    }
                });
            }
        });

        $(document).on('click', '#code-btn', function(){
            $('.code-data').removeClass('table-primary');
            $('#code-detail-field').hide();
            $('#code-create-field').show();
        });

        function selectCodeDetail(codeNum){
            $('#code-btn').removeClass('onClicked');
            $.ajax({
                url: '/system/code/selectCode.do',
                type: 'get',
                data: { codeNum: codeNum },
                dataType: 'json',
                success: function(response){
                    if(response != null){
                        $('.table.detail-code').find('#detail-code-name').val(response.codeName);
                        $('.table.detail-code').find('#detail-code-num').val(response.code);
                        $('.table.detail-code').find('#detail-code-note').val(response.note);
                        $('input[name="codeGroup"]').val(response.codeGroup);
                        $('#code-detail-field').show();
                        $('#code-create-field').hide();
                    } else {
                        $('.table.detail-code').find('#detail-code-name').val('');
                        $('.table.detail-code').find('#detail-code-num').val('');
                        $('.table.detail-code').find('#detail-code-note').val('');
                        $('#code-detail-field').hide();
                        $('#code-create-field').show();
                    }
                },
                error: function(){
                    $('#code-detail-field').hide();
                    $('#code-create-field').show();
                }
            });
        }
    }

    $('#update-code-group-btn').on('click', function(e){
        var form = $('#update-code-group-form')[0];
        if(form.checkValidity() === false){
            e.preventDefault();
            e.stopPropagation();
            form.classList.add('was-validated');
            Swal.fire("warning", "필수입력사항을 입력해 주세요!.", "warning");
            return;
        } else {
            var userConfirmed = confirm("코드그룹을 수정 하시겠습니까?");
            if (userConfirmed) {
                $('#update-code-group-form').submit();
            }
        }
    });

    $(document).on('click', '#delete-code-group-btn', function(e){
        Swal.fire({
            title: "삭제하시겠습니까?",
            icon: 'warning',
            showCancelButton: true, //취소 버튼
            confirmButtonColor: '#ca0f3a', // confrim 버튼 색깔 지정
            confirmButtonText: "삭제", //confirm 버튼 텍스트 지정
            cancelButtonText: "취소" //confirm 버튼 텍스트 지정
        }).then(function(result){
            if(result.value){ //삭제 실행 부분
                $('#update-code-group-form').attr('action', '/system/code/deleteCodeGroup.do');
                $('#update-code-group-form').submit();
            }
        });
    });

    $('#create-code-group-btn').on('click', function(e){
        var form = $('#create-code-group-form')[0];
        if(form.checkValidity() === false){
            e.preventDefault();
            e.stopPropagation();
            form.classList.add('was-validated');
            Swal.fire("warning", "필수입력사항을 입력해 주세요!.", "warning");
            return;
        } else {
            var userConfirmed = confirm("코드그룹을 등록 하시겠습니까?");
            if (userConfirmed) {
                $('#create-code-group-form').submit();
            }
        }
    });

    $(document).on('click', '#create-code-btn', function(e){
        let num = $('.table .code-group-data.table-primary .group-num').text();
        var appendInput = $("<input>").attr({
            type: "hidden",
            name: "codeGroup",
            value: num
        });
        $('#create-code-form').append(appendInput);
        var form = $('#create-code-form')[0];
        if(form.checkValidity() === false){
            e.preventDefault();
            e.stopPropagation();
            form.classList.add('was-validated');
            Swal.fire("warning", "필수입력사항을 입력해 주세요!.", "warning");
            return;
        } else {
            var userConfirmed = confirm("코드를 등록 하시겠습니까?");
            if (userConfirmed) {
                $('#create-code-form').submit();
            }
        }
    });

    $(document).on('click', '#update-code-btn', function(e){
        var codeName = $('input[name="codeName"]');
        var form = $('#update-code-form')[0];
        if(form.checkValidity() === false){
            e.preventDefault();
            e.stopPropagation();
            form.classList.add('was-validated');
            Swal.fire("warning", "필수입력사항을 입력해 주세요!.", "warning");
            return;
        } else {
            var userConfirmed = confirm("코드를 수정 하시겠습니까?");
            if (userConfirmed) {
                $('#update-code-form').submit();
            }
        }
    });

    $(document).on('click', '#delete-code-btn', function(e){
        Swal.fire({
            title: "삭제하시겠습니까?",
            icon: 'warning',
            showCancelButton: true, //취소 버튼
            confirmButtonColor: '#ca0f3a', // confrim 버튼 색깔 지정
            confirmButtonText: "삭제", //confirm 버튼 텍스트 지정
            cancelButtonText: "취소" //confirm 버튼 텍스트 지정
        }).then(function(result){
            if(result.value){ //삭제 실행 부분
                $('#update-code-form').attr('action', '/system/code/deleteCode.do');
                $('#update-code-form').submit();
            }
        });
    });

});

// 코드 인쇄

$(function(){
    let today = new Date();
    let year = today.getFullYear();
    let month = today.getMonth() + 1;
    let date = today.getDate();
    let day = today.getDay();
    $('#today').text(year + '/' + month + '/' + date);

    $('#print-btn').on('click',function(){ //인쇄 기능
        var printContents = $("#printableArea").html();
        $("body").html(printContents);
        window.print();
        window.location.href = window.location.pathname;
    });

    $('#code-category').on('change', function(){
        var selectedVal = $(this).val();
        if(selectedVal == 'all'){
            window.location.href = '/system/code/printCode.do';
        } else {
            $.ajax({
                url:'/system/code/selectPrintCode.do',
                type: 'get',
                data: { groupNum: selectedVal },
                dataType: 'json',
                success: function(response){
                    $('#table-field').empty();
                    var codeGroup = response;
                    $('.point').text(codeGroup.codeList.length);
                    var appendTable = `
                    <table class="table text-center table-bordered table-print">
                        <thead>
                            <tr>
                                <th style="width: 43%;" colspan="2">Group Name / Code Name</th>
                                <th style="width: 10%;">Code</th>
                                <th style="width: *%;">참고사항</th>
                            </tr>
                        </thead>
                        <tr>
                            <td colspan="2">${codeGroup.codeGroupName }</td>
                            <td>${codeGroup.codeGroup }</td>
                            <td>${codeGroup.note }</td>
                        </tr>
                    `;
                    for(var i = 0; i < codeGroup.codeList.length; i++){
                        appendTable += `
                        <tr>
                            <td></td>
                            <td>${codeGroup.codeList[i].codeName }</td>
                            <td>${codeGroup.codeList[i].code }</td>
                            <td>${codeGroup.codeList[i].note }</td>
                        </tr>
                        `;
                    }
                    appendTable += '</table>';
                    $('#table-field').append(appendTable);
                    $('#append-group-name').text(codeGroup.codeGroupName);
                    $('#append-group').text(codeGroup.codeGroup);
                    $('#append-group-note').text(codeGroup.note);
                    for(var i = 0; i < codeGroup.codeList.length; i++){
                        $('.table-print .append-code-name:eq(' + i + ')').text(codeGroup.codeList[i].codeName);
                        $('.table-print .append-code:eq(' + i + ')').text(codeGroup.codeList[i].code);
                        $('.table-print .append-code-note:eq(' + i + ')').text(codeGroup.codeList[i].note);
                    }
                }
            });
        }
    });
});

toastr.options = {
    "closeButton": true, //닫기 버튼 여부
    "debug": false, // 콘솔창 메세지 여부
    "newestOnTop": false, //새로운 팝업창을 띄울 때 위로(true), 아래(false)
    "progressBar": true, // 닫히기까지의 프로그래스바 표시
    "positionClass": "toast-top-center", // 팝업창 위치
    /* Top Right, Bottom Right, Bottom Left, Top Left, Top Full Width, Bottom Full Width, Top Center, Bottom Center, */
    "preventDuplicates": false, // 중복실행 방지, false일 경우 여러번 실행 가능
    "showDuration": "300",
    "hideDuration": "100",
    "timeOut": "2000",
    "extendedTimeOut": "1000",
    "showEasing": "swing",
    "hideEasing": "linear",
    "showMethod": "fadeIn",
    "hideMethod": "fadeOut"
}
// toastr.error('error');// toastr.warning('warning');//  toastr.success('success');// toastr.info('info');
var queryString = window.location.search;
var params = new URLSearchParams(queryString);
var pval = params.get('msg');
//console.log('GET Parameter Value:', pval);
if(pval == 'save'){
    toastr.success('저장을 완료하였습니다.');
} else if(pval == 'modify'){
    toastr.success('수정을 완료하였습니다.');
} else if(pval == "error"){
    toastr.error('작업중 오류가 발생하였습니다.');
} else if(pval == "delete"){
    toastr.success('삭제를 완료하였습니다.');
}
