/*
 * Copyright (c) 2009, Sun Microsystems, Inc.
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without 
 * modification, are permitted provided that the following conditions are met:
 * 
 *  * Redistributions of source code must retain the above copyright notice, 
 *    this list of conditions and the following disclaimer.
 *  * Redistributions in binary form must reproduce the above copyright 
 *    notice, this list of conditions and the following disclaimer in 
 *    the documentation and/or other materials provided with the distribution.
 *  * Neither the name of Sun Microsystems, Inc. nor the names of its 
 *    contributors may be used to endorse or promote products derived 
 *    from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT 
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, 
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED 
 * TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. 
 */
 
#include "vehicle_list.h"


List* List::prev(Vehicle *veh) {
    List *i = this->find(veh);

    if (i->hasValue()) {
        return i->p;
    } else {
        return NULL;    // couldn't find vehicle
    }
}


List* List::next(Vehicle *veh) {
    List *i = this->find(veh);

    if (i->hasValue()) {
        return i->n;
    } else {
        return NULL;    // couldn't find vehicle
    }
}


List* List::find(Vehicle *veh) {
    for (List *i = this->first(); i->hasValue(); i = i->n) {
        if (i->v->name() == veh->name()) {
            return i;
        }
    }
    return NULL; // couldn't find vehicle
}

void List::remove(Vehicle *veh) {
    List *i = this->find(veh);

    if (i->hasValue()) {
        i->p->n = i->n;
        i->n->p = i->p;
    }
}

void List::append(Vehicle *veh) {
    List *i = new List();

    i->v = veh;
    i->n = this;
    i->p = p;
    p->n = i;
    p    = i;
}

void List::prepend(Vehicle *veh) {
    List *i = new List();

    i->v = veh;
    i->p = this;
    i->n = n;
    n->p = i;
    n    = i;
}

void List::insert(Vehicle *veh) {
    // Scan over list looking for element which is smaller than v and then insert v after it
    for (List *i = this->last(); i->hasValue(); i = i->p)  {
        if (i->v->pos() < veh->pos()) {
            i->insertAfter(veh);
            return;
        }
    }

    // If there is no element smaller than v, insert it at the beginning
    this->insertAfter(veh);
}

ostream& operator<<(ostream & o, List & l)
{ 
    o << "{ ";

    for (List *i = l.first(); i->hasValue(); i = i->next()) {
        o << i->value();
        if (i != l.last()){
            o << " , ";
        }
    }

    o << " }" << endl;

    return o;
}

