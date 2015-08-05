<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="top.jsp"%>

<div class="row clearfix">
	<div class="col-md-12 column">
		<h2 class="text-center text-primary">用户注册</h2>
	</div>
</div>
<div class="row clearfix">
	<div class="col-md-3 column"></div>
	<div class="col-md-6 column">
		<form class="form-horizontal" action="user/register" method="post">
			<div class="form-group">
				<label for="userEmail" class="col-sm-2 control-label">登录名</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" id="userEmail" name="userEmail">
				</div>
			</div>
			<div class="form-group">
				<label for="password" class="col-sm-2 control-label">密码</label>
				<div class="col-sm-10">
					<input type="password" class="form-control" id="password" name="password">
				</div>
			</div>
			<div class="form-group">
				<label for="nickName" class="col-sm-2 control-label">昵称</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" id="nickName" name="nickName">
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<button type="submit" class="btn btn-default">注册</button>
				</div>
			</div>
		</form>
	</div>
	<div class="col-md-3 column"></div>
</div>

<%@include file="tail.jsp"%>