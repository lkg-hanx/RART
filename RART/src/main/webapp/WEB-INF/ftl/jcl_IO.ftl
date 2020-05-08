<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>資産情報</title>
	<link rel="stylesheet" href="../../css/bootstrap.css">
</head>

<body>
	<section class="hero p-0">
		<div class="container-fluid">
			<div class="row">
				<div class="col-sm-3 bg-primary-dark py-5">
					<div class="scroll-container scroll-container-1">
						<div class="text-center">
							<div class="mb-5">
								<img class="img" src="../../img/aa.png">
							</div>
							<div>
								<p>ResourceAanalyReportTool_Ver0.1</p>
							</div>
						</div>
						<hr class="hr"/>
						<div class="menu-left">
							<a class="page-scroll" href="../../index.html">はじめにお読みください</a>
						</div>
						<hr class="hr"/>
						<div class="menu-left">
							<span>
								資産現状分析の情報一覧
							</span>
						</div>
						<hr class="hr"/>
						<div class="scroll-content">
							<div class="menu-left">
								<a class="page-scroll" href="../../cobol.html">COBOL情報一覧</a>
							</div>
							<div class="menu-left">	
								<a class="page-scroll" href="../../jcl.html">JCL情報一覧</a>
							</div>
						</div>
					</div>
					<hr style="margin:0px;height:1px;border:0px;background-color:#D5D5D5;color:#D5D5D5;"/>
				</div>
				<div class="col-sm-9 ml-auto px-0">
					<section class="hero" id="id2">
						<div class="container">
							<div>
								<table class="table_border">
								  <tr>
									<td class="td_100 table_border td_title">PGM名</td>
									<td class="td_100 table_border">
									<#if jcl.pgmName??>${jcl.pgmName}<#else>-</#if></td>
									<td class="td_100 table_border td_title">ライブラリ名</td>
									<td class="td_100 table_border">${jcl.raipiraiName}</td>
								  </tr>
								</table>
								<br/>
								<br/>
								<table id="jcl_page" class="table_border">
								  <tr>
									<td class="td_50 table_border td_title">No.</td>
									<td class="td_100 table_border td_title">ステッブ名(EX)</td>
									<td class="td_100 table_border td_title">ファイル名</td>
									<td class="td_100 table_border td_title">ファイル識別名</td>
									<td class="td_100 table_border td_title">レコードキー</td>
									<td class="td_120 table_border td_title">データレコード</td>
									<td class="td_80 table_border td_title">入出力(I/O)</td>
									<td class="td_120 table_border td_title">アクセスモード</td>
									<td class="td_150 table_border td_title">備考</td>
								  </tr>
								  <#list jcl.iosList as iosDto>
								  <tr>
									<td 
										<#if iosDto.ioList?? && (iosDto.ioList?size gt 0) >
											rowspan='${iosDto.ioList?size}'
										</#if>
										style="vertical-align: top;" class="td_50 table_border">${iosDto_index+1}</td>
									
									<td 
										<#if iosDto.ioList?? && (iosDto.ioList?size gt 0) >
											rowspan='${iosDto.ioList?size}'
										</#if> 
										style="vertical-align: top;" class="td_100 table_border">
										<#if iosDto.steveName??>
										${iosDto.steveName}
										<#else>-</#if>
									</td>
									<#if iosDto.ioList?? && (iosDto.ioList?size gt 0) >
										<#list iosDto.ioList as ioDto>
											<td class="td_100 table_border">
												<#if ioDto.filesName??>
												${ioDto.filesName}
												<#else>なし</#if>
											</td>
											<td class="td_100 table_border">
												<#if ioDto.fileslType??>
												${ioDto.fileslType}
												<#else>-</#if>
											</td>
											<td class="td_100 table_border">
												<#if ioDto.recordKey??>
												${ioDto.recordKey}
												<#else>-</#if>
											</td>
											<td class="td_150 table_border">
												<#if ioDto.dateRecordKey??>
												${ioDto.dateRecordKey}
												<#else>-</#if>
											</td>
											<td class="td_80 table_border">
												<#if ioDto.ioType??>
												${ioDto.ioType}
												<#else>-</#if>
											</td>
											<td class="td_120 table_border">
												<#if ioDto.accessMode??>
												${ioDto.accessMode}
												<#else>-</#if>
											</td>
											<td class="td_150 table_border">
												<#if ioDto.notes??>
												${ioDto.notes}
												</#if>
											</td>
										</tr>
										</#list>
									<#else>
										<td class="td_100 table_border">�ʤ�</td>
										<td class="td_100 table_border">-</td>
										<td class="td_100 table_border">-</td>
										<td class="td_150 table_border">-</td>
										<td class="td_80 table_border">-</td>
										<td class="td_120 table_border">-</td>
										<td class="td_150 table_border"></td>
										</tr>
									</#if>
								  </#list>
								</table>
								<br/>
								<div class="center">
									
									<#if jcl.iosList?size / 100 gt 1 >
										<#assign pageNum=1>
										<#list jcl.iosList as ioDto>
											<#if ioDto_index % 100 == 0>
												<a href="javascript:showTr('${pageNum}');">${pageNum}</a>
												<#assign pageNum=pageNum + 1>
											</#if>
										</#list>
										<span id="spanNext">次ページ</span>
									</#if>
								</div>
							</div>
						</div>
					</section>
				</div>
			</div>
		</div>
	</section>
</body>
<script>
	var theTable = document.getElementById("jcl_page");
	var numberRowsInTable = theTable.rows.length;   
	var pageSize=100;
	var totalNum = ${jcl.iosList?size};
	
	
	window.onload=function(){
		if(totalNum>pageSize){
				showTr(1);
		}	
	}
	
	
	function showTr(pageId){
		hide();
		var startNum = 0;
		var endNum = 100;
		
		<#if jcl.iosList?size / 100 gt 1 >
			<#assign ioListSize=0>
			<#assign pageNo=1>
			<#list jcl.iosList as iosDto>
				<#if iosDto.ioList?? && (iosDto.ioList?size gt 0) >
					<#assign ioListSize=ioListSize + iosDto.ioList?size>
				<#else>
					<#assign ioListSize=ioListSize + 1>
				</#if>
				<#if (iosDto_index + 1) % 100 == 0 || (iosDto_index + 1) == jcl.iosList?size>
					var pageEndNum${pageNo}=${ioListSize};
					<#assign pageNo=pageNo + 1>
				</#if>
			</#list>
		</#if>
		
		<#if jcl.iosList?size / 100 gt 1 >
			<#assign pageno=1>
			<#list jcl.iosList as iosDto>
				<#if (iosDto_index + 1) % 100 == 0  || (iosDto_index + 1) == jcl.iosList?size>
					if(pageId == ${pageno}){
						<#if pageno gt 1>
							startNum = pageEndNum${pageno-1};
						</#if>
						endNum = pageEndNum${pageno};
					}
					<#assign pageno=pageno + 1>
				</#if>
			</#list>
		</#if>
		
		theTable.rows[0].style.display = '';
		if(totalNum <= pageId*pageSize){
			for ( var i = startNum; i<endNum; i++ ){   
				if(i+1 < numberRowsInTable){			
					theTable.rows[i+1].style.display = '';    
				}
			}   
			spanNext.innerHTML = "次ページ";
		}else{
			for ( var i = startNum; i<endNum; i++ ){      
				theTable.rows[i+1].style.display = '';    
			} 
			var pageNum = parseInt(pageId) + parseInt("1");
			var url = "<a href=\"javascript:showTr('"+pageNum+"');\">次ページ</a>";
			spanNext.innerHTML = url;
		}

	}
	 

	 //隐藏表格    
	function hide(){ 
		for ( var i = 0; i<numberRowsInTable; i++ ){    
			theTable.rows[i].style.display = 'none'; 
        }			
	}   

   


</script>
</html>
