import axios from 'axios';
function Modal({isOpen,children,closeModal,register}){






    return(<>
        <div style={{display:isOpen?"block":"none"}}>
            <div style={{
                        position: "fixed",
                        top:0,
                        left:0,
                        width:"100vw",
                        height:"100vh",
                    }}> >
            </div>
            <div style={{
                         position:"absolute",
                         top:"50%",
                         left:"50%",
                         transform:"translate(-50%, -50%)",
                         width:800,
                         maxWidth:"100%",
                         maxHeight:"90%",
                         padding:20,
                         overflowY:"auto",
                         backgroundColor:"#E0E0E0"
                     }}>
                <div>{children}</div>
                <button onClick={closeModal}>취소</button>
                <button type="button" onClick={register}>등록</button>

            </div>
        </div>



    </>)
}
export default Modal;
