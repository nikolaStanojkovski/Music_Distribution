import React from 'react';
import Modal from 'react-bootstrap/Modal';
import Pagination from "../../Partial/Pagination/pagination";
import useArtists from "./useArtists";
import viewArtists from "./viewArtists";
import {PAGEABLE, TOTAL_PAGES} from "../../../constants/model";

const Artists = (props) => {

    const {
        artists,
        showModal,
        profilePictureSource,
        setShowModal,
        loadArtists,
        fetchArtistPicture
    } = useArtists(props);

    return (
        <div className={"container mm-4 my-5"}>
            <div className={"row mb-5"}>
                <h1 className="display-5">View our artists</h1>
                <p className="text-muted">Discover who has been releasing music on our platform, and then decide which
                    artists best suit your personal listening preferences.</p>
            </div>
            <hr/>
            <div className={"row"}>
                {
                    viewArtists({artists, fetchArtistPicture})
                }
            </div>
            {(artists && artists[PAGEABLE] && artists[TOTAL_PAGES])
                ? <Pagination changePage={(pageNumber) => loadArtists(pageNumber)}
                              totalPages={artists[TOTAL_PAGES]}
                              pageNumber={artists[PAGEABLE].pageNumber}/>
                : undefined}
            <Modal show={showModal} onHide={() => setShowModal(false)}
                   size="lg"
                   aria-labelledby="contained-modal-title-vcenter"
                   centered>
                <img src={profilePictureSource} alt={"Artist profile"}/>
            </Modal>
        </div>
    )
}

export default Artists;