import {toast} from "react-toastify";
import {FILE_VALIDATION_FAILED} from "../constants/exception";

const FileUtil = {
    downloadBlob(fileName, fileUrl, mimeType) {
        const element = document.createElement('a');
        element.setAttribute(
            'href',
            `data:${mimeType};charset=utf-8,${encodeURIComponent(fileUrl)}`
        );
        element.setAttribute('download', fileName);
        document.body.appendChild(element);
        element.click();
        document.body.removeChild(element);
    },
    convertToBlob(dataURI) {
        if (dataURI && dataURI.length > 0) {
            const byteString = (dataURI.split(',')[0].indexOf('base64') >= 0)
                ? atob(dataURI.split(',')[1])
                : unescape(dataURI.split(',')[1]);

            const mimeString = dataURI.split(',')[0].split(':')[1].split(';')[0];
            const ia = new Uint8Array(byteString.length);
            for (let i = 0; i < byteString.length; i++) {
                ia[i] = byteString.charCodeAt(i);
            }
            return new Blob([ia], {type: mimeString});
        } else {
            return undefined;
        }
    },
    convertAndSaveToString(name, blob) {
        const reader = new FileReader();
        reader.onload = (event) => {
            const blobTarget = event.target.result;
            localStorage.setItem(name, blobTarget.toString());
        }
        reader.readAsDataURL(blob);
    },
    validateFile(file, fileLimit) {
        if (!(file && file instanceof Blob && file.size > 0 && file.size <= fileLimit)) {
            toast.error(FILE_VALIDATION_FAILED);
            return false;
        }
        return true;
    }
}

export default FileUtil;