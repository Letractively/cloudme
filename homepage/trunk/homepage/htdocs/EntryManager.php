<?php
require_once 'config/config_data.php';
require_once 'DeliciousEntryLoader.php';
require_once 'EntryDao.php';
require_once 'FlickrEntryLoader.php';
require_once 'MetaDao.php';
require_once 'RssEntryLoader.php';

class EntryManager {
    private $entryLoaders;

    public function __construct() {
        $this->entryLoaders = array(
            new FlickrEntryLoader(),
            new DeliciousEntryLoader(),
            new RssEntryLoader('http://blog.moritzpetersen.de/feed/', 'http://blog.moritzpetersen.de'),
            new RssEntryLoader('http://feeds.feedburner.com/cloudme-blog?format=xml', 'http://blog.cloudme.org')
        );
    }

    function getEntries() {
        $entryDao = new EntryDao();
        $metaDao = new MetaDao();
        $entries = array();
        $updateDate = $metaDao->readLastUpdateDate();
        if ($this->needsUpdate($updateDate)) {
            foreach ($this->entryLoaders as $loader) {
                foreach ($loader->loadEntries() as $entry) {
                    $entries[] = $entry;
                }
            }
            usort($entries, array("Entry", "compareTo"));
            $entryDao->insertEntries($entries);
        }
        else {
            $entries = $entryDao->readEntries();
        }
        return $entries;
    }

    private function needsUpdate($updateDate) {
        return true;
        global $configData;
        $dao = new EntryDao();
        $updateDate = $dao->getLastUpdateDate();
        return !(isset($updateDate)) OR ($updateDate < time() - $configData['timestamp']);
    }
}
?>
