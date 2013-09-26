<?php

//The names of the variables were altered, so their names can be not true

	function getHighestIndianPosition($indiansArray){
		$max=0;
		for($currentIndianNumber = count($indiansArray)-1; $currentIndianNumber>0;){
			$n=1;
			while($indiansArray[$currentIndianNumber]>$indiansArray[$currentIndianNumber-$n]){
				if($currentIndianNumber==$n){
					if($currentIndianNumber>$max){
							return $currentIndianNumber;
						}
				break 2;
				}
			$n++;
			}
		$c=$currentIndianNumber;
		$currentIndianNumber-=$n-1;
		if($indiansArray[$currentIndianNumber]==$indiansArray[$currentIndianNumber-1]){
			$currentIndianNumber--;
		}
		while($indiansArray[$currentIndianNumber]<$indiansArray[$currentIndianNumber-1]){
			$currentIndianNumber--;
			if($currentIndianNumber==0){
				break;
			}
		}
		if($c-$currentIndianNumber>$max){
			$max=$c-$currentIndianNumber;
			$seesTheMostIndian=$c;
		}
		if(($max)>$currentIndianNumber-1){
			break;
		}
		}
	return $seesTheMostIndian;
	}
?>