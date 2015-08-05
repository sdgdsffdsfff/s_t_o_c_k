<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="top.jsp" %>

	<div class="row clearfix">
		<div class="col-md-12 column">
			<h2 class="text-center text-primary">
				Please Login!
			</h2>
		</div>
	</div>
	<div class="row clearfix">
		<div class="col-md-3 column">
		</div>
		<div class="col-md-6 column">
			<form class="form-horizontal" role="form" action="j_spring_security_check" method="post">
				<div class="form-group">
					<label for="j_username" class="col-sm-2 control-label">用户名</label>
					<div class="col-sm-10">
						<input type="text" name="j_username" class="form-control" id="j_username">
					</div>
				</div>
				<div class="form-group">
					 <label for="j_password" class="col-sm-2 control-label">密码</label>
					<div class="col-sm-10">
						<input type="password" name="j_password" class="form-control" id="j_password">
					</div>
				</div>
				<div class="form-group">
					<div class="col-sm-offset-2 col-sm-10">
						<div class="checkbox">
							 <label><input type="checkbox"> 记住我</label>
						</div>
						<div><font color="red">${error}</font></div>
					</div>
				</div>
				<div class="form-group">
					<div class="col-sm-offset-2 col-sm-10">
						 <button type="submit" class="btn btn-default">登录</button>
					</div>
				</div>
			</form>
		</div>
		<div class="col-md-3 column">
			<div class="alert alert-danger" role="alert">
			还没有帐号?立即
			<a href="user/registerPage"><button type="button" class="btn btn-danger">注册</button></a>
		</div>
		</div>
	</div>

<%@include file="tail.jsp" %>