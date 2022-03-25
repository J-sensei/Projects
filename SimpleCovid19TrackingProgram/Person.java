/**
 * A person contains all the general information, phone and name are status, status is int.
 */
public abstract class Person{
    private String phone;
    private String name;
    private int status; // 1->normal, 2->case, 3->close

    /**
     * A person must contains specific phone number, name and their own status.
     * 
     * @param phone the phone number of this person
     * @param name name of the person
     * @param status status of this person, 1->normal, 2->case, 3->close
     */
    public Person(String phone, String name, int status){
        try{
            setPhone(phone);
            setStatus(status);
        }catch(IllegalArgumentException e){
            System.out.println(e);//print out exception message
        }
        this.name = name;
    }

    /**
     * Perform both checking for phone number and status input is valid or no.
     * 
     * @return Validity of both phone number and status input
     */
    public boolean checkInputValidity(){
        if(phone == null)
            return false;

        if(checkPhoneInput(phone) && checkStatusInput(status))
            return true;
        else
            return false;
    }

    /**
     * Return boolean result of the phone number input validity
     * @param phone String of the phone number
     * @return  Validity of the phone number
     */
    public boolean checkPhoneInput(String phone){
        boolean result = true;

        //test if every char is number
        for(int i = 0; i < phone.length(); ++i){
            if(!Character.isDigit(phone.charAt(i))){ //if contains non number char
                result = false;
            }
        }

        return result;
    }

    /**
     * Check status input, only accept 1, 2 and 3.
     * @param status Status of this person
     * @return Status validity
     */
    public boolean checkStatusInput(int status){
        if(status >= 1 && status <= 3)
            return true;
        else
            return false;
    }

    //setter

    /**
     * Set phone number of this person.
     * @param phone String of the phone number
     * @throws IllegalArgumentException Throw an exception if contains non number character
     */
    public void setPhone(String phone) throws IllegalArgumentException{
        boolean isNumber = checkPhoneInput(phone);

        if(isNumber)
            this.phone = phone;
        else
            throw new IllegalArgumentException("Phone can only be number");
    }

    /**
     * Set status of this person.
     * @param status Current status of this person
     * @throws IllegalArgumentException Throw an exception if the input is out of range
     */
    public void setStatus(int status) throws IllegalArgumentException{
        if(checkStatusInput(status))
            this.status = status;
        else
            throw new IllegalArgumentException("Status can only be 1,2 and 3; 1->normal, 2->case, 3->close");
    }

    //getter

    /**
     * Return phone number.
     * @return String phone
     */
    public String getPhone(){ return phone; }

    /**
     * Return name.
     * @return String name
     */
    public String getName(){ return name; }

    /**
     * Return status.
     * @return Int Status
     */
    public int getStatus(){ return status; }
}
