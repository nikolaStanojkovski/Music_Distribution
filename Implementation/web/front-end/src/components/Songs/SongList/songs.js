import React from 'react';
import Modal from 'react-bootstrap/Modal';
import {API_BASE_URL, AUDIO_STREAM_URL, SONG_COVER_URL} from "../../../constants/endpoints";

const Songs = (props) => {
    const [showModal, setShowModal] = React.useState(false);
    const [imageSource, updateImageSource] = React.useState(undefined);
    const [audioPlayer, updateAudioPlayer] = React.useState(undefined);

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
            const songId = button.parentElement.parentElement.getAttribute('data-id');
            if (songId) {
                playAudio(songId, button);
            }

            togglePlayButton(button);
        }
    }

    const fetchSongCover = (id, isASingle) => {
        if (id && isASingle) {
            updateImageSource(`${API_BASE_URL}${SONG_COVER_URL}/${id}.png`);
            setShowModal(true);
        }
    }

    return (
        <div className={"container mm-4 my-5"}>
            <div className={"row mb-5"}>
                <h1 className="display-5">Send out your songs</h1>
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
                            <th scope={"col"}>Is Published</th>
                            <th scope={"col"}>Is Single</th>
                            <th scope={"col"}>Artist</th>
                            <th scope={"col"}>Album</th>
                            <th scope={"col"}/>
                        </tr>
                        </thead>
                        <tbody>
                        {props.songs.map((term) => {
                            return (
                                <tr key={term.id} data-id={term.id}
                                    className={`${(term['isASingle']) ? 'table-row-clickable' : ''}`}
                                    onClick={() => fetchSongCover(term.id, term['isASingle'])}>
                                    <td>{term.songName}</td>
                                    <td>{term.songGenre}</td>
                                    <td>{term['songLength']['formattedString']}</td>
                                    <td>{(term['isPublished']) ? 'Yes' : 'No'}</td>
                                    <td>{(term['isASingle']) ? 'Yes' : 'No'}</td>
                                    <td>{term['creator']['artistPersonalInfo'].fullName}</td>
                                    <td>{(term['album']) ? term['album'].albumName : ''}</td>
                                    <td>
                                        <button key={term.id} className={`table-item-button bi bi-play`}
                                                onClick={fetchSong}/>
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
                <img src={imageSource} alt={"Song cover image"}/>
            </Modal>
        </div>
    )

}

export default Songs;