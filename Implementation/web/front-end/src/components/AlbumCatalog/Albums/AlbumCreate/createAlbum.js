import React from 'react';
import {Link, useHistory} from 'react-router-dom';

const CreateAlbum = (props) => {

    const History = useHistory();
    const [formData, updateFormData] = React.useState({
        albumName: "",
        genre: 0,

        artistName: "",
        producerName: "",
        composerName: ""
    });

    const handleChange = (e) => {
        updateFormData({
            ...formData,
            [e.target.name]: e.target.value.trim()
        })
    }

    const onFormSubmit = (e) => {
        e.preventDefault();
        const albumName = formData.albumName;
        const genre = formData.genre;
        const totalLength = 0;
        const isPublished = false;
        const artistName = props.selectedArtist.artistPersonalInfo.fullName;
        const producerName = formData.producerName;
        const composerName = formData.composerName;
        const creatorId = props.selectedArtist.id;

        props.createAlbum(albumName, genre, totalLength, isPublished, artistName, producerName, composerName, creatorId);
        History.push("/albums");
    }

    return (
        <div className="container">
            <h1 className={"text-center mt-4"}>Create a new album</h1>
            <br/>
            <div className={""}>
                <div className="col-md-6">
                    <br/>
                    <form onSubmit={onFormSubmit}>
                        <div className="form-group">
                            <label htmlFor="name">Album Name</label>
                            <input type="text"
                                   className="form-control"
                                   id="albumName"
                                   name="albumName"
                                   required
                                   placeholder="Enter album name"
                                   onChange={handleChange}/>
                        </div>
                        <br/>

                        <div className="form-group">
                            <label>Genre</label>
                            <select onChange={handleChange} name="genre" className="form-control">
                                <option value={null}>Select the genre</option>
                                {props.genres.map((term) => {
                                        return <option value={term}>{term}</option>;
                                    }
                                )}
                            </select>
                        </div>
                        <br/>

                        <div className="form-group">
                            <label htmlFor="artistName">Artist Name</label>
                            <input name="artistName" disabled={true}
                                   value={props.selectedArtist.artistPersonalInfo.fullName}
                                   className="form-control disabled"/>
                        </div>
                        <br/>

                        <div className="form-group">
                            <label htmlFor="producerName">Producer Name</label>
                            <input type="text"
                                   className="form-control"
                                   id="producerName"
                                   name="producerName"
                                   required
                                   placeholder={"Enter producer name"}
                                   onChange={handleChange}/>
                        </div>
                        <br/>

                        <div className="form-group">
                            <label htmlFor="composerName">Composer Name</label>
                            <input type="text"
                                   className="form-control"
                                   id="composerName"
                                   name="composerName"
                                   required
                                   placeholder={"Enter composer name"}
                                   onChange={handleChange}/>
                        </div>
                        <br/>

                        <button id="submit" type="submit" className="btn btn-primary">Submit</button>

                        <br/>
                    </form>

                    <Link type="button" className="btn btn-link" to={"/albums"}>Back to albums list</Link>
                </div>
            </div>
        </div>
    );
};

export default CreateAlbum;