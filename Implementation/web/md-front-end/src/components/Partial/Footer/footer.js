const Footer = () => {
    const year = new Date().getFullYear();
    return (
        <footer className="footer mt-5 pt-4 pb-2 bg-dark">
            <p className="text-center text-muted">© {year} Music Distribution, Inc
            </p>
        </footer>
    );
}

export default Footer;