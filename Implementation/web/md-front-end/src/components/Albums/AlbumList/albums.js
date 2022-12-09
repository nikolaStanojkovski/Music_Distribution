import React from 'react';
import Modal from 'react-bootstrap/Modal';
import Pagination from "../../Partial/Pagination/pagination";
import useAlbums from "./useAlbums";
import viewAlbums from "./viewAlbums";
import {KEY, PAGEABLE, TOTAL_PAGES, VALUE} from "../../../constants/model";

const Albums = (props) => {

    const {
        albums,
        filter,
        imageSource,
        showModal,
        searchParams,
        setShowModal,
        fetchAlbums,
        fetchAlbumCover,
        filterAlbums,
        loadAlbums
    } = useAlbums(props);

    React.useEffect(() => {
        fetchAlbums();
    }, []);

    return (
        <div className={"container mm-4 my-5"}>
            <div className={"row mb-5"}>
                <h1 className="display-5">The list of albums</h1>
                <p className="text-muted">Make sure that others may access your albums using our platform.</p>
            </div>
            <hr/>
            <div className={"row my-4"}>
                {viewAlbums({albums, fetchAlbumCover})}
            </div>
            {(albums && albums[PAGEABLE] && albums[TOTAL_PAGES])
                ? <Pagination changePage={(pageNumber) =>
                    (filter && searchParams && searchParams[KEY] && searchParams[VALUE])
                        ? filterAlbums(pageNumber,
                        searchParams[KEY],
                        searchParams[VALUE])
                        : loadAlbums(pageNumber)}
                              totalPages={(albums[TOTAL_PAGES]) ? albums[TOTAL_PAGES] : 0}
                              pageNumber={(albums[PAGEABLE] && albums[PAGEABLE].pageNumber)
                                  ? albums[PAGEABLE].pageNumber
                                  : 0 }/>
                : undefined}
            <Modal show={showModal} onHide={() => setShowModal(false)}
                   size="lg"
                   aria-labelledby="contained-modal-title-vcenter"
                   centered>
                <img src={imageSource} alt={"Album cover image"}/>
            </Modal>
        </div>
    )
}

export default Albums;