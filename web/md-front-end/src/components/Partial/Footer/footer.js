import {ToastContainer} from "react-toastify";
import React from "react";

const Footer = () => {
    const year = new Date().getFullYear();
    return (
        <div>
            <ToastContainer
                position="bottom-right"
                autoClose={4000}
                hideProgressBar={true}
                newestOnTop={true}
                closeOnClick={true}
                pauseOnFocusLoss={true}
                pauseOnHover={true}
                theme="dark"
            />
            <footer className="footer mt-5 pt-4 pb-2 bg-dark">
                <p className="text-center text-muted">© {year} Music Distribution, Inc
                </p>
            </footer>
        </div>
    );
}

export default Footer;