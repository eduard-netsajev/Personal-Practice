
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <META name="author" content="Eduard Netsajev">
        <title></title>
    </head>
    <body>
        <?php 
        include_once 'Phonebook.php';
       







if (!file_exists('contacts.txt')) {
	echo 'Can not find file <b>contacts.txt</b>';
	exit;
}

$data = file('contacts.txt');
if (!is_array($data)) {
	echo 'File <b>contacts.txt</b> is empty';
	exit;
}

$phonebook = new Phonebook();

foreach ($data as $entryData) {
	$entryParts = explode(',', $entryData);
	$phonebookEntry = new PhonebookEntry($entryParts[0], $entryParts[1]);
	if (strlen($entryParts[2]) > 0) {
		$phonebookEntry->setMobilePhone($entryParts[2]);
	}
	if (strlen($entryParts[3]) > 0) {
		$phonebookEntry->setHomePhone($entryParts[3]);
	}
        $phonebook->add($phonebookEntry);
}
//------------------------------------------------
echo '<h1>All entries sorted ascending</h1>';
//------------------------------------------------
foreach ($phonebook->getSorted('asc') as $entry) {
	echo $entry;
}

//------------------------------------------------
echo '<h1>All entries sorted descending</h1>';
//------------------------------------------------
foreach ($phonebook->getSorted('desc') as $entry) {
	echo $entry;
}

//------------------------------------------------
echo '<h1>Entries starting with J</h1>';
//------------------------------------------------
foreach ($phonebook->getByFirstLetter('J') as $entry) {
	echo $entry;
}

//------------------------------------------------
echo '<h1>Entries starting with B</h1>';
//------------------------------------------------
foreach ($phonebook->getByFirstLetter('B') as $entry) {
	echo $entry;
}

//------------------------------------------------
echo '<h1>Entries starting with N</h1>';
//------------------------------------------------
foreach ($phonebook->getByFirstLetter('N') as $entry) {
	echo $entry;
}

//------------------------------------------------
echo '<h1>Entries having 512</h1>';
//------------------------------------------------
foreach ($phonebook->getByPhoneNumber('512') as $entry) {
	echo $entry;
}

//------------------------------------------------
echo '<h1>Entries having 87</h1>';
//------------------------------------------------
foreach ($phonebook->getByPhoneNumber('87') as $entry) {
	echo $entry;
}

//------------------------------------------------
echo '<h1>Entries having 651</h1>';
//------------------------------------------------
foreach ($phonebook->getByPhoneNumber('651') as $entry) {
	echo $entry;
}

        ?>
    </body>
</html>
