import React from 'react';
import ReactDOM from 'react-dom';
import './assets/style/index.css';
import './assets/style/animation.css';
import './assets/style/page.css';
import './assets/style/tick-success.css';
import './assets/style/file-upload.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import "bootstrap-icons/font/bootstrap-icons.css";
import App from './components/App/App';
import reportWebVitals from './reportWebVitals';

ReactDOM.render(
  <React.StrictMode>
    <App />
  </React.StrictMode>,
  document.getElementById('root')
);

reportWebVitals();