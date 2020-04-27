<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>資産情報</title>
	<link rel="stylesheet" href="css/bootstrap.css">
	<script src="js/scripts.js"></script>
</head>

<body>
	<section class="hero p-0">
		<div class="container-fluid">
			<div class="row">
				<div class="col-sm-3 bg-primary-dark py-5 text-center">
					<div class="scroll-container scroll-container-1">
						<div class="text-center">
							<div class="mb-5">
								<img class="img" src="img/aa.png">
							</div>
							<div>
								<p>ResourceAanalyReportTool_Ver0.1</p>
							</div>
						</div>
						<hr class="hr"/>
						<div class="text-center">
							<ul class="menu-left">
								<li class=>
									<a class="page-scroll" href="index.html">はじめにお読みくださ</a>
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
										<a class="page-scroll" href="cobol.html">COBOL情報一覧</a>
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
					<section class="hero" id="id1" style="display:">
						<div class="container">
							<div>
								<h2 class="pt-5 lead_40 center">はじめに</h2>
								<hr class="hr"/>
								<h2 class="lead_20">[制限付権利の説明]</h2>
								<p>
									本製品(ソフトウエアおよびドキュメントを含む)の使用、複製または開示は、Linkage株式会社との契約に記された制約条件に従うものとします。
								</p>
								<p>
									本製品は、著作権、特許権およびその他の知的所有権に関する法律により保護されています。
								</p>
								<br/>
								<h2 class="lead_20">[本書の記載内容について]</h2>
								<p>
									本書には、技術的誤りまたは誤種のあふ可能住があります。
								</p>
								<br/>
								<hr class="hr"/>
								<h2 class="lead_20">[資産情報に図する]</h2>
								<table>
									<tr>
										<td class="td1">ファイル件数：</td>
										<td class="td1">${OutDto.filesSize}</td>
									</tr>
									<tr>
										<td class="td1">総行数：</td>
										<td class="td1">${OutDto.totalNum}</td>
									</tr>
									<tr>
										<td class="td1">有効行数：</td>
										<td class="td1">${OutDto.validNum}</td>
									</tr>
								</table>
								<br/>
								<hr class="hr"/>
								<h2 class="lead_20">[作成情報に図する]</h2>
								<table>
									<tr>
			 							<td class="td1">バージョン：</td>
										<td class="td1">RART_Ver0.1</td>
									</tr>
									<tr>
										<td class="td1">作成時間：</td>
										<td class="td1">${OutDto.systemTime}</td>
									</tr>
									<tr>
										<td class="td1">作成者：</td>
										<td class="td1">${OutDto.user}</td>
									</tr>
								</table>
							</div>
						</div>
					</section>
				</div>
			</div>
		</div>
	</section>
</body>
<script>
 function showSection(objId){
	var objDiv=document.getElementById(objId);  
	var objDiv1=document.getElementById("id1");   
	var objDiv2=document.getElementById("id2"); 
	objDiv1.style.display="none";
	objDiv2.style.display="none";
	objDiv.style.display="";
 }
</script>
</html>
