import React from 'react';
import Modal from 'react-bootstrap/Modal';
import {API_BASE_URL, ARTIST_PICTURE_URL} from "../../../constants/endpoint";
import {Link} from "react-router-dom";
import ScreenElementsUtil from "../../../util/screenElementsUtil";
import Pagination from "../../Partial/Pagination/pagination";

const Artists = (props) => {

    const [showModal, setShowModal] = React.useState(false);
    const [profilePictureSource, updateProfilePictureSource] = React.useState(null);

    const fetchArtistPicture = (e, id) => {
        if (ScreenElementsUtil.isClickableTableRow(e, id)) {
            updateProfilePictureSource(`${API_BASE_URL}${ARTIST_PICTURE_URL}/${id}.png`);
            setShowModal(true);
        }
    }

    return (
        <div className={"container mm-4 my-5"}>
            <div className={"row mb-5"}>
                <h1 className="display-5">View our artists</h1>
                <p className="text-muted">Discover who has been releasing music on our platform, and then decide which
                    artists best suit your personal listening preferences.</p>
            </div>
            <hr/>
            <div className={"row"}>
                <div className={"table-responsive"}>
                    <table className={"table table-striped"}>
                        <thead>
                        <tr>
                            <th scope={"col"}>Email</th>
                            <th scope={"col"}>Telephone number</th>
                            <th scope={"col"}>Name</th>
                            <th scope={"col"}>Art Name</th>
                            <th scope={"col"}>Albums</th>
                            <th scope={"col"}>Songs</th>
                        </tr>
                        </thead>
                        <tbody>
                        {(props.artists && props.artists.content) ? props.artists.content.map((term) => {
                            return (
                                <tr key={term.id} className={"table-row-clickable align-middle"}
                                    onClick={(e) => fetchArtistPicture(e, term.id)}>
                                    <td>{term['userContactInfo'].email['fullAddress']}</td>
                                    <td>{term['userContactInfo'].telephoneNumber}</td>
                                    <td>{term['userPersonalInfo'].fullName}</td>
                                    <td>{term['userPersonalInfo'].artName}</td>
                                    <td className={"table-cell-clickable"}>
                                        <Link to={"/albums?creator_id=" + term.id}
                                              className={`btn btn-outline-secondary btn-block bi bi-list`}>
                                        </Link>
                                    </td>
                                    <td className={"table-cell-clickable"}>
                                        <Link to={"/songs?creator_id=" + term.id}
                                              className={`btn btn-outline-secondary btn-block bi bi-list`}>
                                        </Link>
                                    </td>
                                </tr>
                            );
                        }) : <tr/>}
                        </tbody>
                    </table>
                </div>
            </div>
            {(props.artists && props.artists['pageable'] && props.artists['totalPages'])
                ? <Pagination changePage={(pageNumber) => props.loadArtists(pageNumber)}
                              totalPages={props.artists['totalPages']}
                              pageNumber={props.artists['pageable'].pageNumber}/>
                : undefined}
            <Modal show={showModal} onHide={() => setShowModal(false)}
                   size="lg"
                   aria-labelledby="contained-modal-title-vcenter"
                   centered>
                <img src={profilePictureSource} alt={"Artist profile image"}/>
            </Modal>
        </div>
    )
}

export default Artists;