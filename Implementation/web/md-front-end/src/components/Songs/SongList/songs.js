import React from 'react';
import Modal from 'react-bootstrap/Modal';
import Pagination from "../../Partial/Pagination/pagination";
import useSongs from "./useSongs";
import viewSongs from "./viewSongs";
import {KEY, PAGEABLE, TOTAL_PAGES, VALUE} from "../../../constants/model";
import {DEFAULT_PAGE_SIZE} from "../../../constants/pagination";

const Songs = (props) => {

    const {
        songs,
        imageSource,
        showModal,
        filter,
        searchParams,
        setShowModal,
        fetchSongCover,
        fetchSong,
        filterSongs,
        loadSongs,
        renderTableRowHeader,
        renderTableRowData
    } = useSongs(props);

    return (
        <div className={"container mm-4 my-5"}>
            <div className={"row mb-5"}>
                <h1 className="display-5">The list of songs</h1>
                <p className="text-muted">Allow guests to hear the songs you've always wanted them to hear.</p>
            </div>
            <hr/>
            <div className={"row my-4"}>
                {
                    viewSongs({
                        songs,
                        fetchSongCover,
                        fetchSong,
                        renderTableRowHeader,
                        renderTableRowData
                    })
                }
            </div>
            {(songs && songs[PAGEABLE] && songs[TOTAL_PAGES])
                ? <Pagination changePage={(pageNumber) =>
                    (filter && searchParams && searchParams[KEY] && searchParams[VALUE])
                        ? filterSongs(pageNumber,
                        DEFAULT_PAGE_SIZE,
                        searchParams[KEY],
                        searchParams[VALUE])
                        : loadSongs(pageNumber)}
                              totalPages={(songs[TOTAL_PAGES]) ? songs[TOTAL_PAGES] : 0}
                              pageNumber={(songs[PAGEABLE]) ? songs[PAGEABLE].pageNumber : 0}/>
                : undefined}
            <Modal show={showModal} onHide={() => setShowModal(false)}
                   size="lg"
                   aria-labelledby="contained-modal-title-vcenter"
                   centered>
                <img src={imageSource} alt={"Song cover"}/>
            </Modal>
        </div>
    )
}

export default Songs;