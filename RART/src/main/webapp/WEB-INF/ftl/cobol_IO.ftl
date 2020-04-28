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
								<a class="page-scroll" href="javascript:showSection('id2');">What we do</a>
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
									<#if cobol.pgmName??>${cobol.pgmName}<#else>-</#if></td>
									<td class="td_100 table_border td_title">ライプラリ名</td>
									<td class="td_100 table_border">${cobol.raipiraiName}</td>
								  </tr>
								</table>
								<br/>
								<br/>
								<table id="cobol_page" class="table_border">
								  <tr>
									<td class="td_50 table_border td_title">No.</td>
									<td class="td_100 table_border td_title">ファイル名</td>
									<td class="td_100 table_border td_title">種别名</td>
									<td class="td_100 table_border td_title">レコードキー</td>
									<td class="td_150 table_border td_title">データレコードキー</td>
									<td class="td_80 table_border td_title">入出力(I/O)</td>
									<td class="td_120 table_border td_title">アクセスモード</td>
									<td class="td_150 table_border td_title">備考</td>
								  </tr>
								  <#list cobol.ioList as ioDto>
								  <tr>
									<td class="td_50 table_border">${ioDto_index+1}</td>
									
									<td class="td_100 table_border">
										<#if ioDto.filesName??>
										${ioDto.filesName}
										<#else>-</#if>
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
								</table>
								<br/>
								<div class="center">
									<#if cobol.ioList?size / 500 gt 1 >
										<#assign pageNum=1>
										<#list cobol.ioList as ioDto>
											<#if ioDto_index % 500 == 0>
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
	var theTable = document.getElementById("cobol_page");
	var numberRowsInTable = theTable.rows.length;   
	var pageSize=500;
	
	window.onload=function(){
		if(numberRowsInTable>pageSize){
				showTr(1);
			}	
		}
	}
	
	function showTr(pageId){
		hide();
		theTable.rows[0].style.display = '';
		if(numberRowsInTable <= pageId*pageSize){
			for ( var i = (pageId-1) * pageSize; i<numberRowsInTable; i++ ){   
				if(i+1 < numberRowsInTable){			
					theTable.rows[i+1].style.display = '';    
				}
			}   
			spanNext.innerHTML = "次ページ";
		}else{
			for ( var i = (pageId-1) * pageSize; i<pageId*pageSize; i++ ){    
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
