import React from 'react';

const Songs = (props) => {
    const [audio, updateAudio] = React.useState(null);

    const togglePlayButton = (button) => {
        button.classList.toggle('bi-play');
        button.classList.toggle('bi-stop');
    }

    const toggleButton = (button) => {
        if (button && button instanceof HTMLElement) {
            togglePlayButton(button);
            const songId = button.parentElement.parentElement.getAttribute('data-id');
            Array.from(document.querySelectorAll(".play-pause-button")).forEach((btn) => {
                const itemSongId = btn.parentElement.parentElement.getAttribute('data-id');
                if (itemSongId !== songId && btn.classList.contains('bi-stop')) {
                    togglePlayButton(btn);
                }
            });
        }
    }

    const fetchSong = (e) => {
        const button = e.target;
        if (button && button instanceof HTMLElement) {
            const songId = button.parentElement.parentElement.getAttribute('data-id');
            if (songId) {
                if (button.classList.contains('bi-play')) {
                    // TODO: Stream audio through the API call

                    // new Audio('http://localhost:8082/api/resource/songs/file/' + songId).addEventListener('load', () => {
                    //     updateAudio(audio);
                    //     audio.play();
                    // });


                    // props.fetchSong(songId).then((data) => {
                    //     if(data) {
                    //         const audioContext = new (window.AudioContext || window.webkitAudioContext)();
                    //         const buffer = new Buffer(data.data);
                    //         console.log(buffer.buffer);
                    //         audioContext.decodeAudioData(buffer.buffer).then((audio) => {
                    //             const sound = audioContext.createBufferSource();
                    //             sound.buffer = audio;
                    //             sound.connect(audioContext.destination);
                    //             sound.start(audioContext.currentTime);
                    //         }).catch((error) => console.error(error));
                    //     }
                    // });
                } else {
                    // if(audio) {
                    //     audio.stop();
                    // }
                }
            }

            toggleButton(button);
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
                            <th scope={"col"}>Is Single</th>
                            <th scope={"col"}>Artist</th>
                            <th scope={"col"}>Album</th>
                            <th scope={"col"}/>
                        </tr>
                        </thead>
                        <tbody>
                        {props.songs.map((term) => {
                            let isASingle = term.isASingle === true ? "Yes" : "No";

                            let artist = "None";
                            let album = "None";
                            if (term.creator !== null && term.creator !== undefined)
                                artist = term.creator.artistPersonalInfo.firstName + " " + term.creator.artistPersonalInfo.lastName;
                            if (term.album !== null && term.album !== undefined)
                                album = term.album.albumName;

                            return (
                                <tr key={term.id} data-id={term.id}>
                                    <td>{term.songName}</td>
                                    <td>{term.songGenre}</td>
                                    <td>{term.songLength.songLengthFormatted}</td>
                                    <td>{isASingle}</td>
                                    <td>{artist}</td>
                                    <td>{album}</td>
                                    <td>
                                        <button key={term.id} className={`play-pause-button bi bi-play`}
                                                onClick={fetchSong}/>
                                    </td>
                                </tr>
                            );
                        })}
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    )
}

export default Songs;