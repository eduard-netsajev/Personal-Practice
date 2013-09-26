<?php
require_once 'view/indiansHeader.html';
require_once 'lib/indiansVizualization.php';
require_once 'lib/highestIndian.php';

// tests

$indians = array(175, 210, 169, 200, 183, 176, 163, 179, 232, 160, 142);

$position = getHighestIndianPosition($indians);

drawIndians($indians, $position);


require_once 'view/indiansFooter.html';
?>