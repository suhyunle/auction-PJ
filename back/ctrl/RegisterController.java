package back.ctrl;

import back.model.domain.RequestDTO;
import back.service.RegisterService;

public class RegisterController {
    private RegisterService service;

    public RegisterController(RegisterService service){
        this.service = service;
    }

    public RegisterController(){
        // service = new TodoService();
    }

    public int registerItem(RequestDTO request){
        System.out.println(">>>> debug ctrl register");

        return service.registerService(request);
    } 
}
