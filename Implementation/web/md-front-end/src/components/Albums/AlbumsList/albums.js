import React from 'react';
import Modal from 'react-bootstrap/Modal';
import {ALBUM_COVER_URL, API_BASE_URL} from "../../../constants/endpoint";
import {Link} from "react-router-dom";
import ScreenElementsUtil from "../../../util/screenElementsUtil";
import Pagination from "../../Partial/Pagination/pagination";
import SearchParamUtil from "../../../util/searchParamUtil";

const Albums = (props) => {
    const [filter, setFilter] = React.useState(false);
    const [showModal, setShowModal] = React.useState(false);
    const [imageSource, updateImageSource] = React.useState(undefined);

    React.useEffect(() => {
        const searchParams = SearchParamUtil.getSearchParams();
        if (searchParams && searchParams.key && searchParams.value) {
            props.filterAlbums(0, searchParams.key, searchParams.value);
            setFilter(true);
        } else {
            props.loadAlbums(0);
        }
    }, []);

    const fetchAlbumCover = (e, id) => {
        if (ScreenElementsUtil.isClickableTableRow(e, id)) {
            updateImageSource(`${API_BASE_URL}${ALBUM_COVER_URL}/${id}.png`);
            setShowModal(true);
        }
    }

    return (
        <div className={"container mm-4 my-5"}>
            <div className={"row mb-5"}>
                <h1 className="display-5">The list of albums</h1>
                <p className="text-muted">Make sure that others may access your albums using our platform.</p>
            </div>
            <hr/>
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
                        {(props.albums && props.albums.content) ? props.albums.content.map((term) => {
                            return (
                                <tr key={term.id} className={'table-row-clickable align-middle'}
                                    onClick={(e) => fetchAlbumCover(e, term.id)}>
                                    <td>{term.albumName}</td>
                                    <td>{term['totalLength']['formattedString']}</td>
                                    <td>{term['genre']}</td>
                                    <td>{(term['creator'] && term['creator']['artistPersonalInfo'])
                                        ? term['creator']['artistPersonalInfo'].fullName : ''}</td>
                                    <td>{(term['albumInfo']) ? term['albumInfo']['artistName'] : ''}</td>
                                    <td>{(term['albumInfo']) ? term['albumInfo']['producerName'] : ''}</td>
                                    <td>{(term['albumInfo']) ? term['albumInfo']['composerName'] : ''}</td>
                                    <td className={"table-cell-clickable"}>
                                        <Link to={"/songs?album_id=" + term.id}
                                              className={`btn btn-outline-secondary btn-block bi bi-list`}>
                                        </Link>
                                    </td>
                                </tr>
                            );
                        }) : null}
                        </tbody>
                    </table>
                </div>
            </div>
            {(props.albums && props.albums['pageable'] && props.albums['totalPages'])
                ? <Pagination changePage={(pageNumber) => (filter)
                    ? props.filterAlbums(pageNumber,
                        SearchParamUtil.getSearchParams()['key'],
                        SearchParamUtil.getSearchParams()['value'])
                    : props.loadAlbums(pageNumber)}
                              totalPages={(props.albums['totalPages']) ? props.albums['totalPages'] : 0}
                              pageNumber={(props.albums['pageable']) ? props.albums['pageable'].pageNumber : 0}/>
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