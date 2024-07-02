/**
 * UI적용 / 김경모 / 24.01.11
 */

$(function() {

	memuSelecter();
	function memuSelecter() {// 메뉴 선택 적용
		var currentPath = window.location.pathname;
        	$('.nav-menu li').removeClass('active open');
        	var $currentMenuItem = $('.nav-menu a[href="' + currentPath + '"]').closest('li');
        	$currentMenuItem.addClass('active');
        	$currentMenuItem.parents('li').addClass('active open');
		console.log("currentPath:",currentPath);
   	}

	function fetchData(companyNumber) { // 상세정보 요청
    	$.ajax({
    		type: 'GET',
    		url: '/system/company/selectCompany.do/' + companyNumber,
    		success: function(response) {
    			$('#detail-field').empty(); // 기존 내용을 비움
    			$('#detail-field').html(response); // 새로운 내용으로 채움
    		},
    	});
    }

	
	const form = $('form[name="searchPage"]');

	$('#button-addon2').on('click', function(){ // 검색 기능
		let searchType = $('#searchType').val();
		let searchName = $('#searchName').val();
		if(searchName == null || searchName == ''){
			window.location.href = window.location.pathname;
		}else{
    		form.find('input[name="searchType"]').val(searchType);
    		form.find('input[name="searchName"]').val(searchName);
    		form.submit();
		}
	});
	
	//상태값 처리
	function updateSearchNameField() {
        var selectedType = $('#searchType').val();
        var currentValue = $('#searchName').val();
        if (selectedType === 'use') {
            var selectElement = '<select class="form-control" id="searchName">' +
                                '<option value="정상"' + (currentValue === '정상' ? ' selected' : '') + '>정상</option>' +
                                '<option value="중지"' + (currentValue === '중지' ? ' selected' : '') + '>중지</option>' +
                                '</select>';
            $('#searchName').replaceWith(selectElement);
        } else {
            $('#searchName').replaceWith(
                '<input type="text" class="form-control" placeholder="Search" id="searchName" value="'+currentValue+'">'
            );
        }
    }
    
    updateSearchNameField();// 초기 페이지 로드 시 검색설정

    $('#searchType').change(function() {// searchType 변경 시 설정
        updateSearchNameField();
    });
	
	toDays();
	function toDays(){ //인쇄 페이지 날짜 적용
		let today = new Date();   
	    let year = today.getFullYear();
	    let month = today.getMonth() + 1;
	    let date = today.getDate(); 	  	    
		$('#today').text(year + ' / ' + month + ' / ' + date);
	} 

	$('#print-btn').on('click',function(){ //인쇄 기능
		var printContents = $("#printableArea").html();   
	 	$("body").html(printContents);
     	window.print();
     	window.location.href = window.location.pathname;
  	});
	
	
	
	$('#companyPage .page-item').on('click', function(){ // 페이지 기능
		let pageNum = $(this).find('.page-link').data('pagenum');
		if(pageNum != pageVO_pageNum && pageNum > 0){
    		form.find('input[name="pageNum"]').val(pageNum);
    		form.submit();
		}else{
			return;
		}
	});
	
	$('table').on('click', '.company-item', function() { //  상세 정보
		$('.company-item').removeClass('table-primary');
    	$('#add-company-btn').removeClass('onClicked');
    	$('#add-company-btn').html("<i class='fal fa-plus'></i> 기업등록");
		$(this).addClass('table-primary');
        let companyNumber = $(this).children('.company-number').text();
        if(!$('#detail-field').is(':empty') && $('.table.select-company-table #detail-number').text() == companyNumber){
        }else{
            $.ajax({
                type: 'GET',
                url: '/system/company/selectCompany.do',
                data: { num: companyNumber },
                success: function(response) {
					$('#detail-field').empty();
                    $('#detail-field').html(response);
                    
                },
            });
        }
    });
	
	$('#add-company-btn').on('click', function(){
        window.location.href = '/system/company/companyInsert.do';
    });

    
    $('#detail-field').on('click','#address-search-btn', function(){ // 주소찾기 실행
			new daum.Postcode({
				oncomplete : function(data) {
					$('input[name="address1"]').val(data.address);
				}
			}).open();
		});
		
	$('#detail-field').on('click','input[name="address1"]', function(){  // 주소 직접입력 방지
		 $('#address-search-btn').trigger('click');
	});

   		 $('#detail-field').on('click', '.switcher-indicator', function() { //사용상태변경
			const aa = $('[name="used"]').prop('checked') ? 0 : 1;
			//console.log('data-state 값:', aa);
        });
    	


      	$('#detail-field').on('change', '#image-input',function(){ //이미지 미리보기
    	  var imageContainer = $('#image-container');
    	  if(this.files && this.files[0]){
    		  var file = this.files[0];
    		  
    		  if (file.type.startsWith('image/')) {
    	          var reader = new FileReader();

    	          reader.onload = function (e) {
    	            var img = $('<img>').attr({
    	              src: e.target.result,
    	              'id':'company-ci'
    	            });
    	         // 이미지 크기를 조정
                    img.css({
                        'max-height': '150px',  // 최대 높이를 150px로 설정
                        'width': 'auto',        // 자동으로 너비 조정
                        'margin-top': '10px'  // 10px의 top margin 추가
                    });
    	            imageContainer.empty();

    	            // 이미지를 표시할 요소에 이미지 추가
    	            imageContainer.append(img);
    	          };
    	          reader.readAsDataURL(file);
    	  	  }else{
        		  alert('이미지 파일이 아닙니다.');
        		  imageContainer.empty();
        		  $(this).val('');
        	  }
    	  }
      });

    	//$('input[name="ceo_phone"]').val(formatPhoneNumber('${company.ceo_phone }'));      
      $('#detail-field').on('input', 'input[name="ceo_phone"]',function(){ //전화번호 
    	  $(this).val(formatPhoneNumber($(this).val()));
      });

  $('#detail-field').on("keyup","input:text[numberOnly]", function() {
      $(this).val($(this).val().replace(/[^0-9]/g,""));
   });
      
      function formatPhoneNumber(input) { //전화번호 자동 변경
    	    let phoneNumber = input.replace(/\D/g, '');

    	    if (phoneNumber.length > 3 && phoneNumber.length <= 7) {
    	      phoneNumber = phoneNumber.slice(0, 3) + '-' + phoneNumber.slice(3);
    	    } else if (phoneNumber.length > 7) {
    	      phoneNumber = phoneNumber.slice(0, 3) + '-' + phoneNumber.slice(3, 7) + '-' + phoneNumber.slice(7);
    	    }

    	    if (phoneNumber.length > 13) {
    	      phoneNumber = phoneNumber.slice(0, 13);
    	    }

    	    return phoneNumber;
    	  }

       
      $('#detail-field').on('click','#id-search-btn', function(){ // 상세보기창 id 모달창 실행
    	  $('#searchText').val('');
   	   $.ajax({
   		  url:"/system/company/searchIdList.do",
   		  method: 'post',
   		  data:{com_number : $('input[name="com_number"]').val()},
   		  dataType:'json',
   		  success: function(response){
   			  let content =  $('#search-id-list');
   			  content.empty();
   			  var cnt = response.cnt;
   			  var data = response.resultList;
   			  $('#search-cnt').text(cnt);
   			  for(var i=0; i<data.length; i++){
   				  var name = data[i].name;
   				  var id = data[i].id;
   				  var pw = data[i].password;
   					
   				  var append_html = '<tr class="id-item">' + 
						  						'<td>' + name + '</td>' + 
						  						'<td>' + id + '</td>' + 
						  						'<td style="display:none;">' + pw + '</td>' + 
						  				 '</tr>';
					content.append(append_html);
   			  }
   		  }
   	   });
   	   $('#searchId').modal('show');
      });
       
     $('#detail-field').on('click', '#search-id-btn', function(){ // 상세보기창id 검색 버튼
   	  var selected = $('#category').val();
   	  var inputText = $('#searchText').val();
   	  
   	  if(selected == 'select'){
   		  alert('옵션을 선택해주세요.');
   		  return;
   	  }else if(inputText == ''){
   		  alert('검색어를 입력하세요');
   		  return;
   	  }
   	  
   	  $.ajax({
   		  url:'/system/company/searchId.do',
   		  method:'post',
   		  contentType: 'application/json',
   		  data :JSON.stringify({
   			  searchType : selected,
   			  searchText : inputText,
   			  com_number : $('input[name="com_number"]').val()
   		  }),
   		  dataType:'json',
   		  success: function(response){
   			  let content =  $('#search-id-list');
   			  content.empty();
   			  var cnt = response.cnt;
   			  var data = response.resultList;
   			  $('#search-cnt').text(cnt);
   			for(var i=0; i<data.length; i++){
 				  var name = data[i].name;
 				  var id = data[i].id;
 				  var pw = data[i].password;
 				  
 				  var append_html = '<tr class="id-item">' + 
						  						'<td>' + name + '</td>' + 
						  						'<td>' + id + '</td>' +
						  						'<td style="display:none;">' + pw + '</td>' + 
						  				 '</tr>';
					content.append(append_html);
 			  }

   		  }
   	  });
     });

     $('#detail-field').on('click', '.id-item', function(){// id정보 폼에 입력
   	  let name = $(this).children('td:nth-child(1)').text();
   	  let id = $(this).children('td:nth-child(2)').text();
   	  let pw = $(this).children('td:nth-child(3)').text();
   	  $('#searchId').modal('hide');
   	  $('input[name="admin_name"]').val(name);
   	  $('input[name="admin_id"]').val(id);
   	  $('input[name="admin_pw"]').val(pw);   	  
     });        



    $('#update-company-btn').on('click', function(event) {
        var form = $('#update-company-form')[0];
        if (form.checkValidity() === false) {
            event.preventDefault();
            event.stopPropagation();
            form.classList.add('was-validated');
            Swal.fire("warning", "필수입력사항을 입력해 주세요!.", "warning");
            return;
        } else {
            var userConfirmed = confirm("기업정보를 수정 하시겠습니까?");
            if (userConfirmed) {
                // Add num value to the form
                var numValue = $('#detail-number').text();
                var numInput = $("<input>").attr({
                    type: "hidden",
                    name: "num",
                    value: numValue
                });
                $('#update-company-form').append(numInput);

                // Submit the form
                $('#update-company-form').submit();
            }
        }
    });




$('#delete-company-btn').on('click', function() {
    var num = $('#detail-number').text(); // Example: Fetching the company number from somewhere

    $.ajax({
        url: '/system/company/deleteCompany.do',
        type: 'POST',
        data: { num: num },
        success: function(response) {
            console.log('Company deleted successfully');
            // AJAX 요청이 성공하면 특정 URL로 리다이렉트
            window.location.href = '/system/company/companyList.do';
        },
        error: function(xhr, status, error) {
            // Handle error
            console.error('Error deleting company:', error, num);
        }
    });
});


  $(document).on('click', '#insert-company-btn', function(event) {
      event.preventDefault(); // 기본 동작 막기

      var form = $('#insert-company-form')[0];
      // 부트스트랩 유효성 검사 실행
      if (form.checkValidity() === false) {
          event.stopPropagation();
          form.classList.add('was-validated');
          Swal.fire("warning", "필수입력사항을 입력해 주세요!.", "warning");
          return;
      } else {
          var userConfirmed = confirm("기업을 등록 하시겠습니까?");
          if (userConfirmed) {
              var addData = { use: $('[name="used"]').prop('checked') ? 1 : 0 };
              $.each(addData, function(key, value) {
                  var appendInput = $("<input>").attr({
                      type: "hidden",
                      name: key,
                      value: value
                  });
                  $('#insert-company-form').append(appendInput);
              });
              $('#insert-company-form').submit();
          }
      }
  });

	 $(document).on('click', 'button.close', function(){
		 $('#searchId').modal('hide');
	 });
	
	$(function(){
		$('button[type="submit"]').on('click', function(){
			$('#search-form').submit();
		});
		
		$('.form-control').keydown(function(e){
			if(e.keyCode === 13){
				$('button[type="submit"]').click();
			}
		});
	})

	////////////////// Toast //////////////////////
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
	if(pval == 'save'){	toastr.success('저장을 완료하였습니다.');	
	}else if(pval == 'modify'){	toastr.success('수정을 완료하였습니다.');	
	}else if(pval =="error"){ toastr.error('기업고유번호가 중복됩니다.');	
	}else if(pval =="delete"){ toastr.success('삭제를 완료하였습니다.');	
	}else if(pval =="delete_error"){ toastr.error('삭제를 실패 하였습니다.');	
	}


});

