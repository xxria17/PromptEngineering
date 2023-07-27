import {useState, useRef} from "react"

const Modal = ({setModalOpen, setSelectedImage, selectedImage, generateVariations}) => {
    const [error, setError] = useState(null)

    const ref = useRef(null)
    const closeModal = () => {
        setModalOpen(false)
        setSelectedImage(null)
    }

    const checkSize = () => {
        if (ref.current.width == 400 && ref.current.height == 400) {
            generateVariations()
        } else {
            setError('Error: Choose 400x400 image')
        }
    }

    return (
        <div className="modal">
            <div onClick={closeModal}>✖</div>
            <div className="img-container">
                {selectedImage && <img ref={ref} src={URL.createObjectURL(selectedImage)} alt="uploaded image"/>}
            </div>
            <p>{error || "* Image must be 400 x 400"}</p>
            {!error && <button onClick={checkSize}>Generate</button>}
            {error && <button onClick={closeModal}>Close this and try again</button>}
        </div>
    )
}

export default Modal