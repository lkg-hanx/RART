<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>RART</title>
	<link rel="stylesheet" href="css/bootstrap.css">
	<script language="javascript"> 
		function showSection(objId){
			var objDiv=document.getElementById(objId);  
			<#list OutDto as dto>
				var objDiv${dto_index}=document.getElementById("id${dto_index}");   
				objDiv${dto_index}.style.display="none";
			</#list>
			objDiv.style.display="";
		}
	</script>
</head>

<body>
	<section class="hero p-0">
		<div class="container-fluid">
			<div class="row">
				<div class="col-sm-3 bg-primary-dark py-5 text-center">
					<div class="mb-5 text-center">
						<img style="max-width:750px;max-height:250px;border:0;margin:0" src="img/aa.png">
					</div>
					<div class="scroll-container scroll-container-1">
						<div class="scroll-content">
							<ul class="menu-left">
								<#list OutDto as dto>
									<li class="nav-item"><a class="page-scroll" href="javascript:showSection('id${dto_index}');">${dto.menu}</a> </li>
								</#list>
							</ul>
						</div>
					</div>
				</div>
				<div class="col-sm-9 ml-auto px-0">
					<#list OutDto as dto>
						<section class="hero" id="id${dto_index}" style="display:">
							<div class="container">
								<div>
									<h2 class="pt-5 lead_40">${dto.title}</h2>
									<p class="lead_20">
										${dto.content}
									</p>
								</div>
							</div>
						</section>
					</#list>
				</div>
			</div>
		</div>
	</section>

</body>
</html>
