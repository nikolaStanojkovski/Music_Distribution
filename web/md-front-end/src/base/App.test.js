import React from "react";
import {render} from '@testing-library/react';
import '@testing-library/jest-dom';
import App from './App';

test('renders learn react link', () => {
    render(<App/>);
    const appElement = document.getElementById("app");
    const mainElement = document.getElementById("main");
    expect(appElement).toBeInTheDocument();
    expect(mainElement).toBeInTheDocument();
});
