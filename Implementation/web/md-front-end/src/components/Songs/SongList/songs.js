import React from 'react';
import Modal from 'react-bootstrap/Modal';
import {API_BASE_URL, AUDIO_STREAM_URL, SONG_COVER_URL} from "../../../constants/endpoint";
import ScreenElementsUtil from "../../../util/screenElementsUtil";
import Pagination from "../../Partial/Pagination/pagination";
import SearchParamUtil from "../../../util/searchParamUtil";
import AuthUtil from "../../../util/authUtil";

const Songs = (props) => {
    const [filter, setFilter] = React.useState(false);
    const [showModal, setShowModal] = React.useState(false);
    const [imageSource, updateImageSource] = React.useState(undefined);
    const [audioPlayer, updateAudioPlayer] = React.useState(undefined);

    React.useEffect(() => {
        const searchParams = SearchParamUtil.getSearchParams();
        if (searchParams && searchParams.key && searchParams.value) {
            props.filterSongs(0, searchParams.key, searchParams.value);
            setFilter(true);
        } else {
            props.loadSongs(0);
        }
    }, []);

    const togglePlayButtonClassList = (button) => {
        button.classList.toggle('bi-play');
        button.classList.toggle('bi-stop');
    }

    const togglePlayButton = (button) => {
        if (button && button instanceof HTMLElement) {
            togglePlayButtonClassList(button);
            const songId = button.parentElement.parentElement.getAttribute('data-id');
            Array.from(document.querySelectorAll(".play-pause-button")).forEach((btn) => {
                const itemSongId = btn.parentElement.parentElement.getAttribute('data-id');
                if (itemSongId !== songId && btn.classList.contains('bi-stop')) {
                    togglePlayButtonClassList(btn);
                }
            });
        }
    }

    const playAudio = (songId, button) => {
        if (button.classList.contains('bi-play')) {
            if (audioPlayer) {
                audioPlayer.pause();
            }
            const audio = new Audio(`${API_BASE_URL}${AUDIO_STREAM_URL}/${songId}.mp3`);
            audio.play().catch((error) => console.error(error));
            updateAudioPlayer(audio);
        } else {
            if (audioPlayer) {
                audioPlayer.pause();
            }
        }
    }

    const fetchSong = (e) => {
        const button = e.target;
        if (button && button instanceof HTMLElement) {
            const buttonCellWrapper = button.parentElement;
            if (buttonCellWrapper && buttonCellWrapper instanceof HTMLElement) {
                const tableRow = buttonCellWrapper.parentElement;
                if (tableRow && tableRow instanceof HTMLElement
                    && tableRow.hasAttributes()) {
                    const songId = tableRow.getAttribute('data-id');
                    if (songId) {
                        playAudio(songId, button);
                    }
                }
            }


            togglePlayButton(button);
        }
    }

    const fetchSongCover = (e, id, isASingle) => {
        if (ScreenElementsUtil.isClickableTableRow(e, id) && isASingle) {
            updateImageSource(`${API_BASE_URL}${SONG_COVER_URL}/${id}.png`);
            setShowModal(true);
        }
    }

    return (
        <div className={"container mm-4 my-5"}>
            <div className={"row mb-5"}>
                <h1 className="display-5">The list of songs</h1>
                <p className="text-muted">Allow guests to hear the songs you've always wanted them to hear.</p>
            </div>
            <hr/>
            <div className={"row my-4"}>
                <div className={"table-responsive"}>
                    <table className={"table table-striped"}>
                        <thead>
                        <tr>
                            <th scope={"col"}>Name</th>
                            <th scope={"col"}>Genre</th>
                            <th scope={"col"}>Length</th>
                            {
                                (AuthUtil.isAuthorized())
                                    ? <th scope={"col"}>Is Published</th> : null
                            }
                            <th scope={"col"}>Is Single</th>
                            <th scope={"col"}>Artist</th>
                            <th scope={"col"}>Album</th>
                            <th scope={"col"}/>
                        </tr>
                        </thead>
                        <tbody>
                        {(props.songs && props.songs.content) ? props.songs.content.map((term) => {
                            return (
                                <tr key={term.id} data-id={term.id}
                                    className={`${(term['isASingle']) ? 'table-row-clickable' : ''} align-middle`}
                                    onClick={(e) => fetchSongCover(e, term.id, term['isASingle'])}>
                                    <td>{term.songName}</td>
                                    <td>{term.songGenre}</td>
                                    <td>{term['songLength']['formattedString']}</td>
                                    {
                                        (AuthUtil.isAuthorized())
                                            ? <td>{(term['isPublished']) ? 'Yes' : 'No'}</td> : null
                                    }
                                    <td>{(term['isASingle']) ? 'Yes' : 'No'}</td>
                                    <td>{term['creator']['userPersonalInfo'].fullName}</td>
                                    <td>{(term['album']) ? term['album'].albumName : ''}</td>
                                    <td className={"table-cell-clickable"}>
                                        <button onClick={fetchSong}
                                                className={`btn btn-outline-secondary btn-block bi bi-play`}>
                                        </button>
                                    </td>
                                </tr>
                            );
                        }) : null}
                        </tbody>
                    </table>
                </div>
            </div>
            {(props.songs && props.songs['pageable'] && props.songs['totalPages'])
                ? <Pagination changePage={(pageNumber) => (filter)
                    ? props.filterSongs(pageNumber,
                        SearchParamUtil.getSearchParams()['key'],
                        SearchParamUtil.getSearchParams()['value'])
                    : props.loadSongs(pageNumber)}
                              totalPages={(props.songs['totalPages']) ? props.songs['totalPages'] : 0}
                              pageNumber={(props.songs['pageable']) ? props.songs['pageable'].pageNumber : 0}/>
                : undefined}
            <Modal show={showModal} onHide={() => setShowModal(false)}
                   size="lg"
                   aria-labelledby="contained-modal-title-vcenter"
                   centered>
                <img src={imageSource} alt={"Song cover image"}/>
            </Modal>
        </div>
    )
}

export default Songs;