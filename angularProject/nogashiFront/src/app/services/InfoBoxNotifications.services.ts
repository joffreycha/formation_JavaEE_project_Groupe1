import { Subject } from 'rxjs/Subject';


export class InfoBoxNotificationsService
{
    messagesSubject = new Subject<any[]>();
    private uniqueId: number = 0;
    private messages = [];
    
    emitMessagesSubject() 
    {
      this.messagesSubject.next(this.messages.slice());
    }

    addMessage(type, message, duration)
    {
        let context = this;
        let id = this.uniqueId++;

        let msg = {
            id: id,
            type: type,
            message: message,
            handler: setTimeout(function()
            {
                context.removeMessage(id);
            }, duration * 1000)
        };

        this.messages.push(msg);
        this.emitMessagesSubject();
    }

    removeMessage(id)
    {
        for(let i=0, nb=this.messages.length; i<nb; i++)
        {
            if(this.messages[i].id==id)
            {
                if(this.messages[i].handler!=null)
                    clearTimeout(this.messages[i].handler);
                this.messages[i].handler = null;
                
                this.messages.splice(i,1);
                this.emitMessagesSubject();
                break;
            }
        }
    }

    getMessageById(id: number) 
    {
        const message = this.messages.find((m) => 
        {
            return (m.id === id);
        });
        return message;
    }
}