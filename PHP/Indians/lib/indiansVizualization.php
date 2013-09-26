<?php

/**
 * Outputs visual representation of indians heights and highlights the number of 
 * people that can be seen by the indian at specified position.
 *
 * @param array $indiansArray indians heights array
 * @param int $highestPosition the position of indian whose sight will be highlighted
 */
 
function drawIndians ($indiansArray, $highestPosition = -1) {
	
	// reversing array to make traversal easier
	$indiansArray = array_reverse($indiansArray, true);
	
	// html table rows for bars, heights and position numbers
	$html = array('bars' => '', 'heights' => '', 'positions' => '');
	
	// flag for start/stop highlighting
	$highlightNext = false;
	
	// number of people that specified indian sees
	$visible = 0;
	
	foreach ($indiansArray as $index => $height) {
		if ($index == $highestPosition) { // highlight the indian at specified position
			$html['bars'] = '<td><div class="theOne" style="height:'.$height.'px"></td>'.$html['bars'];
			$highlightNext = true; // turn on the highlighting flag
		} else {
			if ($highlightNext) {
				$html['bars'] = '<td><div class="seen" style="height:'.$height.'px"></td>'.$html['bars'];
				$visible++;
				if ($indiansArray[$highestPosition] <= $height && 
						$indiansArray[$index-1] <= $height) {
					$highlightNext = false; // shut down highlighting flag
				}
			} else {
				$html['bars'] = '<td><div style="height:'.$height.'px"></td>'.$html['bars'];
			}
		}
		// add height and position to the apropriate arrays
		$html['heights'] = '<td class="hght">'.$height.'</td>'.$html['heights'];
		$html['positions'] = '<td class="pos">'.$index.'</td>'.$html['positions'];
	}
	
	echo '<h2>Indian with index '.$highestPosition.' sees '.$visible.' indian'.($visible != 1 ? 's' : '').'</h2>';
	
	// output html table representation
	echo '<table><tr>'.$html['bars'].'</tr><tr>'.$html['heights'].
				'</tr><tr>'.$html['positions'].'</tr></table>';
}

?>