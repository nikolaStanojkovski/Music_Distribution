import useCheckout from "./useCheckout";
import viewCheckout from "./viewCheckout";

const Checkout = (props) => {

    const {message, displayCheckmark, resultWatermark} = useCheckout(props);

    return viewCheckout(
        {
            message,
            displayCheckmark,
            resultWatermark
        }
    );
}

export default Checkout;