import React from 'react';
import Modal from 'react-bootstrap/Modal';
import {API_BASE_URL, ALBUM_COVER_URL} from "../../../constants/endpoints";

const Albums = (props) => {

    const [showModal, setShowModal] = React.useState(false);
    const [imageSource, updateImageSource] = React.useState(undefined);

    const fetchAlbumCover = (id) => {
        if (id) {
            updateImageSource(`${API_BASE_URL}${ALBUM_COVER_URL}/${id}.png`);
            setShowModal(true);
        }
    }

    return (
        <div className={"container mm-4 my-5"}>
            <div className={"row mb-5"}>
                <h1 className="display-5">Distribute your albums</h1>
                <p className="text-muted">Make sure that others may access your albums using our platform.</p>
            </div>
            <hr />
            <div className={"row my-4"}>
                <div className={"table-responsive"}>
                    <table className={"table table-striped"}>
                        <thead>
                        <tr>
                            <th scope={"col"}>Name</th>
                            <th scope={"col"}>Length</th>
                            <th scope={"col"}>Genre</th>
                            <th scope={"col"}>Publisher</th>
                            <th scope={"col"}>Artist</th>
                            <th scope={"col"}>Producer</th>
                            <th scope={"col"}>Composer</th>
                            <th scope={"col"}>Songs</th>
                        </tr>
                        </thead>
                        <tbody>
                        {props.albums.map((term) => {
                            return (
                                <tr key={term.id} className={'table-row-clickable'}
                                    onClick={() => fetchAlbumCover(term.id)}>
                                    <td>{term.albumName}</td>
                                    <td>{term['totalLength']['formattedString']}</td>
                                    <td>{term['genre']}</td>
                                    <td>{term['creator']['artistPersonalInfo'].fullName}</td>
                                    <td>{(term['albumInfo']) ? term['albumInfo']['artistName'] : ''}</td>
                                    <td>{(term['albumInfo']) ? term['albumInfo']['producerName'] : ''}</td>
                                    <td>{(term['albumInfo']) ? term['albumInfo']['composerName'] : ''}</td>
                                    <td>
                                        <button key={term.id}
                                                className={`table-item-button bi bi-list`}
                                                onClick={() => {}}/>
                                    </td>
                                </tr>
                            );
                        })}
                        </tbody>
                    </table>
                </div>
            </div>
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