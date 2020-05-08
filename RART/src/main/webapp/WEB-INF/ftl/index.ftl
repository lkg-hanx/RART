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
				<div class="col-sm-3 bg-primary-dark py-5">
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
						<div class="menu-left">
							<a class="page-scroll" href="index.html">はじめにお読みください</a>
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
								<a class="page-scroll" href="cobol.html">COBOL情報一覧</a>
							</div>
							<div class="menu-left">	
								<a class="page-scroll" href="jcl.html">JCL情報一覧</a>
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
								<h2>[制限付権利の説明]</h2>
								<p class="lead_16">
									本製品(ソフトウェアおよびドキュメントを含む)の使用、複製または開示は、Linkage株式会社との契約に記された制約条件に従うものとします。
								</p>
								<p class="lead_16">
									本製品は、著作権、特許権およびその他の知的所有権に関する法律により保護されています。
								</p>
								<br/>
								<h2>[本書の記載内容について]</h2>
								<p class="lead_16">
									本書には、技術的な誤りまたは誤植のある可能性があります。
								</p>
								<br/>
								<hr class="hr"/>
								<h2>[資産情報に関する]</h2>
								<table>
									<tr>
										<td class="td_200 lead_16">FILE件数：</td>
										<td class="td_200 lead_16">${OutDto.filesSize}</td>
									</tr>
									<tr>
										<td class="td_200 lead_16">総行数：</td>
										<td class="td_200 lead_16">${OutDto.totalNum}</td>
									</tr>
									<tr>
										<td class="td_200 lead_16">有効行数：</td>
										<td class="td_200 lead_16">${OutDto.validNum}</td>
									</tr>
								</table>
								<br/>
								<hr class="hr"/>
								<h2>[作成情報に関する]</h2>
								<table>
									<tr>
			 							<td class="td_200 lead_16">バージョン：</td>
										<td class="td_200 lead_16">RART_Ver0.1</td>
									</tr>
									<tr>
										<td class="td_200 lead_16">作成時間：</td>
										<td class="td_200 lead_16">${OutDto.systemTime}</td>
									</tr>
									<tr>
										<td class="td_200 lead_16">作成者：</td>
										<td class="td_200 lead_16">${OutDto.user}</td>
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
