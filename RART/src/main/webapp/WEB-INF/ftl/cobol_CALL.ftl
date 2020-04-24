<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>資産情報</title>
	<link rel="stylesheet" href="../css/bootstrap.css">
</head>

<body>
	<section class="hero p-0">
		<div class="container-fluid">
			<div class="row">
				<div class="col-sm-3 bg-primary-dark py-5 text-center">
					<div class="scroll-container scroll-container-1">
						<div class="text-center">
							<div class="mb-5">
								<img class="img" src="../img/aa.png">
							</div>
							<div>
								<p>ResourceAanalyReportTool_Ver0.1</p>
							</div>
						</div>
						<hr class="hr"/>
						<div class="text-center">
							<ul class="menu-left">
								<li class=>
									<a class="page-scroll" href="../index.html">はじめにお読みくださ</a>
								</li>
							</ul>
						</div>
						<hr class="hr"/>
						<div class="text-center">
							<ul class="menu-left">
								<li class=>
									<a class="page-scroll" href="javascript:showSection('id1');">資産現状分析の情報一覧</a>
								</li>
							</ul>
						</div>
						<hr class="hr"/>
						<div class="scroll-content">
							<div>
								<ul class="menu-left">
									<li class="nav-item">
										<a class="page-scroll" href="../cobol.html">COBOL情報一覧</a>
									</li>
									<li class="nav-item">
										<a class="page-scroll" href="javascript:showSection('id2');">What we do</a>
									</li>
								</ul>
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
									<td class="td2 table_border td_title">PGM名</td>
									<td class="td2 table_border">${cobol.pgmName}</td>
									<td class="td2 table_border td_title">ライプライ名</td>
									<td class="td2 table_border">${cobol.raipiraiName}</td>
								  </tr>
								</table>
								<br/>
								<br/>
								<table id="cobol_page" class="table_border">
								  <tr>
									<th class="td2 table_border td_title">No</th>
									<th class="td2 table_border td_title">階層1</th>
									<th class="td2 table_border td_title">階層2</th>
									<th class="td2 table_border td_title">階層3</th>
									<th class="td2 table_border td_title">階層4</th>
									<th class="td2 table_border td_title">階層5</th>
									<th class="td2 table_border td_title">階層6</th>
									<th class="td2 table_border td_title">階層7</th>
									<th class="td2 table_border td_title">階層8</th>
									<th class="td2 table_border td_title">階層9</th>
									<th class="td2 table_border td_title">階層10</th>
								  </tr>
								  <#list cobol.callList as callDto>
								  <tr>
									<td class="td2 table_border">${callDto_index+1}</td>
									<td class="td2 table_border">
									<#if callDto.stratum1??>
										${callDto.stratum1}
									</#if>
									</td>
									<td class="td2 table_border">
									<#if callDto.stratum2??>
										${callDto.stratum2}
									</#if>
									</td>
									<td class="td2 table_border">
									<#if callDto.stratum3??>
										${callDto.stratum3}
									</#if>
									</td>
									<td class="td2 table_border">
									<#if callDto.stratum4??>
										${callDto.stratum4}
									</#if>
									</td>
									<td class="td2 table_border">
									<#if callDto.stratum5??>
										${callDto.stratum5}
									</#if>
									</td>
									<td class="td2 table_border">
									<#if callDto.stratum6??>
										${callDto.stratum6}
									</#if>
									</td>
									<td class="td2 table_border">
									<#if callDto.stratum7??>
										${callDto.stratum7}
									</#if>
									</td>
									<td class="td2 table_border">
									<#if callDto.stratum8??>
										${callDto.stratum8}
									</#if>
									</td>
									<td class="td2 table_border">
									<#if callDto.stratum9??>
										${callDto.stratum9}
									</#if>
									</td>
									<td class="td2 table_border">
									<#if callDto.stratum10??>
										${callDto.stratum10}
									</#if>
									</td>
								  </tr>
								  </#list>
								</table>
								<br/>
								<div class="center">
									<#if cobol.callList?size / 500 gt 1 >
										<#assign pageNum=1>
										<#list cobol.callList as callDto>
											<#if callDto_index % 500 == 0>
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
