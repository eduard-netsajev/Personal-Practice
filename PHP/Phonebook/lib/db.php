<?php

$link;

function connect_db() {

	global $link;

	$link = mysql_connect('localhost', 'root', '');
	if (!$link) {
    die('Could not connect: ' . mysql_error());
	}

	$db_selected = mysql_select_db('telefoniraamat', $link);
	if (!$db_selected) {
	    die ('Can\'t use database raamatukogu: ' . mysql_error());
	}

}

?>