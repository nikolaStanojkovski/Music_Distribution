import ScreenElementsUtil from "../../../util/screenElementsUtil";
import React from "react";

const viewCheckout = (props) => {
    return (
        <div>
            <div className="checkout-wrapper">
                <div className={"text-center"} hidden={props.displayCheckmark}>
                    <section className="c-container">
                        <progress className="pure-material-progress-circular"/>
                    </section>
                    <br/>
                    <div className={"row"}>
                        <div className={"col text-center"}>
                            <p className="text-muted blinking-text \">
                                The transaction is being processed &#46; &#46; &#46;
                            </p>
                        </div>
                    </div>
                </div>

                <div className={"checkmark-wrapper"} hidden={!props.displayCheckmark}>
                    <div onClick={() => ScreenElementsUtil.reloadDomain()}>
                        <section className="c-container">
                            <div className="o-circle c-container__circle o-circle__sign--success clickable"
                                 hidden={!props.resultWatermark}>
                                <div className="o-circle__sign"/>
                            </div>
                            <div className="o-circle c-container__circle o-circle__sign--failure"
                                 hidden={props.resultWatermark}>
                                <div className="o-circle__sign"/>
                            </div>
                        </section>
                    </div>
                    <br/>
                    <div className={"row"}>
                        <div className={"col text-center"}>
                            <p className={(props.resultWatermark) ? 'text-success mt-lg-1' : 'text-danger mt-lg-1'}>
                                <b>{props.message}</b>
                            </p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default viewCheckout;